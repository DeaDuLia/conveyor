package com.application.entity;

import com.application.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;



import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "application_id", nullable = false)
    private long applicationId;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit credit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "applied_offer")
    @Type(type = "jsonb")
    private LoanOffer appliedOffer;

    @Column(name = "sign_date")
    private LocalDate signDate;

    @Column(name = "ses_code")
    private int sesCode;

    @Column
    @Type(type = "jsonb")
    private StatusHistory status_history;
}
