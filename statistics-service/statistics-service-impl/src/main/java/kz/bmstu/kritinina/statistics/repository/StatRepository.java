package kz.bmstu.kritinina.statistics.repository;

import kz.bmstu.kritinina.statistics.model.StatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatRepository extends JpaRepository<StatEntity, Long> {
    Optional<StatEntity> findByMethodAndEndpoint(String method, String endpoint);
}