package de.flxplzk.frontend.backend.service;

import com.google.common.collect.Lists;
import de.flxplzk.frontend.backend.domain.Report;
import de.flxplzk.frontend.backend.domain.ReportingPeriod;
import de.flxplzk.frontend.backend.domain.Transaction;
import de.flxplzk.frontend.backend.domain.TransactionType;
import de.flxplzk.frontend.backend.processor.Processor;
import de.flxplzk.frontend.backend.repository.ReportRepository;
import de.flxplzk.frontend.backend.repository.TransactionRepository;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

import static de.flxplzk.frontend.backend.domain.Transaction.TransactionSpecifications.forReportingPeriod;

public class ReportService implements Subscribable<Report> {

    private final ReportRepository reportRepository;
    private final List<ServiceListener<Report>> listeners = Lists.newArrayList();
    private final Processor<List<Transaction>, ByteArrayOutputStream> processor;
    private final TransactionRepository transactionRepository;

    public ReportService(ReportRepository reportRepository, Processor<List<Transaction>, ByteArrayOutputStream> processor, TransactionRepository transactionRepository) {
        this.reportRepository = reportRepository;
        this.processor = processor;
        this.transactionRepository = transactionRepository;
    }

    public List<Report> findAll() {
        return Lists.newArrayList(this.reportRepository.findAll());
    }

    public void delete(Report report) {
        this.reportRepository.delete(report);
        this.listeners.forEach(listener -> listener.change(new ChangeEvent<>(report)));
    }

    public void save(Report report) {
        report.setCreated(LocalDateTime.now());
        List<Transaction> transactions = this.transactionRepository.findAllByTransactionDateAfterAndTransactionDateBeforeAndTransactionTypeEquals(report.getReportStart(), report.getReportEnd(), TransactionType.SB);
        report.setFile(this.processor.process(transactions).toByteArray());
        this.reportRepository.save(report);
        this.listeners.forEach(listener -> listener.change(new ChangeEvent<>(report)));
    }

    @Override
    public Registration addListener(ServiceListener<Report> listener) {
        this.listeners.add(listener);
        return () -> this.listeners.remove(listener);
    }
}
