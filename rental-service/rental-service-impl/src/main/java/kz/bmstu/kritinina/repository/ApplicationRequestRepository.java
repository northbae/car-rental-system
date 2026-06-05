package kz.bmstu.kritinina.repository;

import kz.bmstu.kritinina.model.entity.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {
    ApplicationRequest findByApplicationUid(UUID applicationUid);
    List<ApplicationRequest> findAllByUsername(String username);
    List<ApplicationRequest> findAllByDepartmentId(String departmentId);
}