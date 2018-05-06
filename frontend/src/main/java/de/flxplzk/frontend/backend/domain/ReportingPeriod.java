package de.flxplzk.frontend.backend.domain;

import java.time.LocalDate;

public class ReportingPeriod {

    private final LocalDate from;
    private final LocalDate to;

    public ReportingPeriod(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
}
