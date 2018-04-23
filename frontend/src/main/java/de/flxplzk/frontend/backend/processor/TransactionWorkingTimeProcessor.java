package de.flxplzk.frontend.backend.processor;

import de.flxplzk.frontend.backend.domain.Transaction;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TransactionWorkingTimeProcessor implements Processor<Transaction, Long> {
    @Override
    public Long process(Transaction input) {
        LocalDateTime start = input.getStart();
        LocalDateTime end = input.getEnd();
        long minutes = start.until(end, ChronoUnit.MINUTES);
        return minutes - input.getMinutesBreak();
    }
}
