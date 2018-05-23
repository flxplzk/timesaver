package de.flxplzk.frontend.backend.domain;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.persistence.metamodel.CollectionAttribute;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    @Column(name = Transaction_.ID)
    private long id;

    @ManyToOne
    @Column(name = Transaction_.EMPLOYEE)
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(name = Transaction_.TRANSACTION_TYPE)
    private TransactionType transactionType;

    @Column(name = Transaction_.TRANSACTION_DATE)
    private LocalDate transactionDate;

    @Column(name = Transaction_.START)
    private LocalDateTime start;

    @Column(name = Transaction_.END)
    private LocalDateTime end;

    @Column(name = Transaction_.MINUTES_BREAK)
    private int minutesBreak;

    @Column(name = Transaction_.CREATION_DATE)
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = Transaction_.BILLING_STATUS)
    private BillingStatus billingStatus;

    @Column(name = Transaction_.AMOUNT)
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

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public BillingStatus getBillingStatus() {
        return billingStatus;
    }

    public void setBillingStatus(BillingStatus billingStatus) {
        this.billingStatus = billingStatus;
    }

    public static class Transaction_ implements ColumnDefinition {
        public static final String TRANSACTION_TYPE = "TRANSACTION_TYPE";
        public static final String TRANSACTION_DATE = "TRANSACTION_DATE";
        public static final String START = "START";
        public static final String END = "END";
        public static final String MINUTES_BREAK = "MINUTES_BREAK";
        public static final String BILLING_STATUS = "BILLING_STATUS";
        public static final String AMOUNT = "AMOUNT";
        public static final String EMPLOYEE = "EMPLOYEE";
    }

    public static class TransactionSpecifications {

        public static Specification<Transaction> forReportingPeriod(ReportingPeriod reportingPeriod) {
            return (root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.between(
                            root.get(Transaction_.TRANSACTION_DATE),
                            reportingPeriod.getFrom(),
                            reportingPeriod.getTo()
                    );
        }

        public static Specification<Transaction> forEmployeeSummary(final Employee employee) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                    .equal(root.get(Transaction_.EMPLOYEE), employee);
        }
    }
}
