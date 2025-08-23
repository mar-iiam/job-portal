package com.jobportal.job_portal.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(
        name = "job_applications",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"job_id", "applicant_id"}) // ✅ Prevents duplicate applications
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedQueries({
        @NamedQuery(
                name = "JobApplication.findByJobId",
                query = "SELECT ja FROM JobApplication ja WHERE ja.job.id = :jobId"
        ),
        @NamedQuery(
                name = "JobApplication.findByApplicant",
                query = "SELECT ja FROM JobApplication ja WHERE ja.applicant.username = :username"
        )
})
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "applied_date", nullable = false, updatable = false)
    private Date appliedDate;

    @PrePersist
    protected void onCreate() {
        if (appliedDate == null) {
            appliedDate = new Date();
        }
    }
}
