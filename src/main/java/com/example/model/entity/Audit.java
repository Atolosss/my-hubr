package com.example.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Embeddable
@Data
public class Audit {
    @Column(name = "create_date_time", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @Column(name = "update_date_time")
    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}
