package kz.bmstu.kritinina.repository;

import kz.bmstu.kritinina.model.entity.DepartmentQuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentQuotaRepository extends JpaRepository<DepartmentQuota, String> {
}