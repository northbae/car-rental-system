package kz.bmstu.kritinina.logging.repository;

import kz.bmstu.kritinina.logging.model.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {
    List<LogEntity> findAllByServiceName(String serviceName);
    List<LogEntity> findAllByLevel(String level);
    List<LogEntity> findAllByServiceNameAndLevel(String serviceName, String level);
}