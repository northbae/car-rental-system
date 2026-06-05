package kz.bmstu.kritinina.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "partner")
public class Partner {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "disabled", nullable = false)
    private boolean disabled;
}