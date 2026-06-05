package kz.bmstu.kritinina.queue.consumer;

import kz.bmstu.kritinina.client.PaymentClient;
import kz.bmstu.kritinina.client.RentalClient;
import kz.bmstu.kritinina.queue.config.KafkaConfig;
import kz.bmstu.kritinina.queue.message.RetryMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryConsumer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RentalClient rentalClient;
    private final PaymentClient paymentClient;

    @KafkaListener(topics = KafkaConfig.RETRY_TOPIC, groupId = "gateway-group")
    public void processRetryMessage(RetryMessage message) {
        log.info("Received retry message: {}", message);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            switch (message.getOperationType()) {
                case "FINISH_RENTAL":
                    handleFinishRental(message);
                    break;
                case "CANCEL_RENTAL":
                    handleCancelRental(message);
                    break;
                case "CANCEL_PAYMENT":
                    handleCancelPayment(message);
                    break;
                default:
                    log.warn("Unknown operation type: {}", message.getOperationType());
            }

        } catch (Exception e) {
            log.error("Failed to process retry message: {}", e.getMessage());
            message.setRetryCount(message.getRetryCount() + 1);
            message.setFailureReason(e.getMessage());
            if (message.getRetryCount() < 5) {
                kafkaTemplate.send(KafkaConfig.RETRY_TOPIC, message);
            } else {
                log.error("Max retries reached for message: {}", message);
            }
        }
    }

    private void handleFinishRental(RetryMessage message) {
        UUID rentalUid = UUID.fromString((String) message.getPayload().get("rentalUid"));
        rentalClient.finishRental(rentalUid);
    }

    private void handleCancelRental(RetryMessage message) {
        UUID rentalUid = UUID.fromString((String) message.getPayload().get("rentalUid"));
        rentalClient.cancelRental(rentalUid);
    }

    private void handleCancelPayment(RetryMessage message) {
        UUID paymentUid = UUID.fromString((String) message.getPayload().get("paymentUid"));
        paymentClient.cancelPayment(paymentUid);
    }
}