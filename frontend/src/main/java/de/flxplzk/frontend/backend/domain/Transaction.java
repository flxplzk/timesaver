package de.flxplzk.frontend.backend.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDate transactionDate;
    private LocalDateTime start;
    private LocalDateTime end;
    private int minutesBreak;
    private LocalDateTime created;
}
