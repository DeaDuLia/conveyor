package com.application.entity;

import com.application.enums.ApplicationStatus;
import com.application.enums.ChangeType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class StatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id; // ????

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "timestamp", nullable = false)
    private LocalDate timeStamp;

    @Column(name = "change_type", nullable = false)
    private ChangeType changeType;

}
