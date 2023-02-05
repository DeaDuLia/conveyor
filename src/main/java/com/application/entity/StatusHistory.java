package com.application.entity;

import com.application.enums.ApplicationStatus;
import com.application.enums.ChangeType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class StatusHistory {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "timestamp")
    private LocalDate timeStamp;

    @Column(name = "change_type")
    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

}
