# Car Rental Microservices System

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue)
![Kubernetes](https://img.shields.io/badge/Kubernetes-Enabled-326ce5)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Messaging-ff6600)
![Build Status](https://github.com/northbae/car-rental-system/actions/workflows/classroom.yml/badge.svg)

A comprehensive **Car Rental System** built using a microservices architecture. The system handles car inventory, rental bookings, payments, and user authentication, designed with high availability and fault tolerance in mind.

## Architecture Overview

The system follows the **Database per Service** pattern and uses a **Custom Gateway** as the single entry point.

*   **Communication:** All inter-service communication is vertical (Gateway → Service). There are no horizontal (Service ↔ Service) synchronous HTTP calls.
*   **Database:** Each service manages its own logical database (PostgreSQL schemas), ensuring loose coupling.
*   **Orchestration:** Fully containerized with Docker and deployed on a Managed Kubernetes Cluster using Helm Charts.

### Services & Ports

| Service | Port | Description |
| :--- | :--- | :--- |
| **Gateway Service** | `8080` | Entry point, Aggregation, Routing, Auth Validation |
| **Car Service** | `8050` | Manages car inventory and availability |
| **Rental Service** | `8060` | Manages rental lifecycle (booking, finishing, history) |
| **Payment Service** | `8070` | Handles financial transactions |

## Tech Stack

*   **Core:** Java, Spring Boot, Spring Cloud
*   **Data:** PostgreSQL, Liquibase (Migrations)
*   **Messaging:** RabbitMQ (Asynchronous processing)
*   **Security:** Keycloak (OIDC), JWT, JWKs
*   **Testing:** JUnit, Mockito
*   **DevOps:** Docker, Kubernetes (Ingress, Helm), Github Actions
*   **Documentation:** Swagger / OpenAPI

## Key Features & Patterns

### 1. Resilience & Circuit Breaker
The Gateway Service implements the **Circuit Breaker** pattern for read operations:
*   If a downstream service fails `N` times, the circuit opens.
*   **Fallbacks:**
    *   Returns empty objects or arrays.
    *   Returns cached data or default strings (if type-safe).
    *   Returns objects with partial data (e.g., only IDs) to maintain linking.

### 2. Distributed Transactions & Consistency
For operations affecting multiple services (e.g., Booking a Car), the system handles failures gracefully with **Saga** pattern:
*   **Rollback:** Complete rollback of the operation if a critical step fails.
*   **Retry Queue:** If a non-critical external system is down during a successful user operation, the request is sent to a **RabbitMQ** queue for asynchronous retry by the Gateway.

### 3. Security
*   **Identity Provider:** Keycloak (OpenID Connect).
*   **Flow:** Resource Owner Password Flow.
*   **Token:** JWT (JSON Web Token) is passed between services.
*   **Validation:** Services validate tokens locally using JWKs (JSON Web Key Set).
*   **Protection:** All `/api/**` endpoints (except auth/callback) require a valid Bearer token.

### 4. Health Checks
Every service implements a `/manage/health` endpoint returning `200 OK`. This is used by:
*   **Kubernetes:** For liveness/readiness probes.
*   **CI/CD:** Github Actions `wait-script.sh` ensures services are up before running tests.

## API Reference

### Authentication
*   **Login:** `POST /api/v1/authorize`
*   **Callback:** `POST /api/v1/callback`

### Car Inventory
*   **Get Cars:** `GET /api/v1/car?page={page}&size={size}`
    *   *Query Param:* `showAll=true` to include reserved cars.

### Rental Operations
*   **Get User Rentals:** `GET /api/v1/rental`
*   **Get Specific Rental:** `GET /api/v1/rental/{rentalUid}`
*   **Book a Car:** `POST /api/v1/rental`
    *   *Logic:* Checks car existence → Locks car → Processes Payment → Creates Rental Ticket.
    *   *Body:*
        ```json
        {
          "carUid": "uuid",
          "dateFrom": "2023-10-01",
          "dateTo": "2023-10-05"
        }
        ```
*   **Finish Rental:** `POST /api/v1/rental/{rentalUid}/finish`
    *   Unlocks the car and updates status to `FINISHED`.
*   **Cancel Rental:** `DELETE /api/v1/rental/{rentalUid}`
    *   Unlocks car, cancels payment, updates status to `CANCELED`.

## Deployment

The project uses **Helm Charts** for universal deployment configuration across services.

1.  **Build:** Images are built and pushed to the Docker Registry.
2.  **Deploy:**
    ```bash
    helm install release-name ./charts/backend-service --set image.repository=...
    ```
3.  **Ingress:** An Ingress Controller exposes the Gateway Service to the outside world.

## Testing

Integration tests verify the "Health" of the architecture using wait scripts:
```bash
# Example wait script used in CI
./wait-for.sh -t 120 "http://localhost:8080/manage/health" -- echo "Gateway is active"
```
