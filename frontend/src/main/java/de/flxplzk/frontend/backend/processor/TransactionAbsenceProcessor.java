package de.flxplzk.frontend.backend.processor;

import de.flxplzk.frontend.backend.domain.Transaction;

public class TransactionAbsenceProcessor implements Processor<Transaction, Long> {
    @Override
    public Long process(Transaction input) {
        long weeklyHours = input.getEmployee().getEmployeeProfile().getWeeklyHours();
        long weeklyMinutes = weeklyHours * 60;
        return weeklyMinutes / 5;
    }
}
