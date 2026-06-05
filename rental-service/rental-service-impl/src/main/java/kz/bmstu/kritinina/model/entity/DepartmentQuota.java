package kz.bmstu.kritinina.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "department_quota")
public class DepartmentQuota {
    @Id
    @Column(name = "department_id")
    private String departmentId;

    @Column(name = "total_quota", nullable = false)
    private int totalQuota;

    @Column(name = "used_quota", nullable = false)
    private int usedQuota;
}