package com.application.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class PaymentScheduleElement implements java.io.Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private int number;

    @Column
    private LocalDate date;

    @Column
    private double totalPayment;

    @Column
    private double interestPayment;

    @Column
    private double debtPayment;

    @Column
    private double remainingDebt;

}
