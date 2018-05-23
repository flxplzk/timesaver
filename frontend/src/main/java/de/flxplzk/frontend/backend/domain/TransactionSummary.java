package de.flxplzk.frontend.backend.domain;

import com.google.common.collect.Maps;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class TransactionSummary {

    private final Employee employee;

    private final Map<YearMonth, Long> workingAmounts;

    private TransactionSummary(final Employee employee, final Map<YearMonth, Long> workingAmounts) {
        this.employee = employee;
        this.workingAmounts = workingAmounts;
    }

    public static TransactionSummary of(final List<Transaction> transactions) {
        final TransactionSummary transactionSummarry =
                new TransactionSummary(transactions.get(0).getEmployee(), Maps.newHashMap());

        Map<YearMonth, Long> workingAmounts = transactionSummarry.getWorkingAmounts();

        for (Transaction transaction : transactions) {

            if (workingAmounts.containsKey(YearMonth.from(transaction.getTransactionDate()))) {
                workingAmounts.put(YearMonth.from(transaction.getTransactionDate()),
                        transaction.getAmount());
            } else {
                workingAmounts.computeIfPresent(YearMonth.from(transaction.getTransactionDate()),
                        (key, oldValue) -> oldValue + transaction.getAmount());
            }
        }
        return transactionSummarry;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Map<YearMonth, Long> getWorkingAmounts() {
        return workingAmounts;
    }
}
