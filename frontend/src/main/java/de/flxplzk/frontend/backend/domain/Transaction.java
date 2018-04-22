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

    @Enumerated(EnumType.STRING)
    private BillingStatus billingStatus;
    private long amount;

    public Transaction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getMinutesBreak() {
        return minutesBreak;
    }

    public void setMinutesBreak(int minutesBreak) {
        this.minutesBreak = minutesBreak;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount){
        this.amount = amount;
    }
}
