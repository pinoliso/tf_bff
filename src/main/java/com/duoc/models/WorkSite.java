package com.duoc.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "work_sites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    private String notes;

    private String environmentalManager;

    private String totalWaste;

    private String constructorCompany;

    // Relaciones
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "work_site_type_id", nullable = false)
    private WorkSiteType workSiteType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "work_site_status_id", nullable = false)
    private WorkSiteStatus workSiteStatus;

    // Auditoría
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

