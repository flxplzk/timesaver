package de.flxplzk.frontend.backend.service;

import com.google.common.collect.Lists;
import de.flxplzk.frontend.backend.domain.BillingStatus;
import de.flxplzk.frontend.backend.domain.Transaction;
import de.flxplzk.frontend.backend.domain.TransactionType;
import de.flxplzk.frontend.backend.processor.Processor;
import de.flxplzk.frontend.backend.processor.TransactionAbsenceProcessor;
import de.flxplzk.frontend.backend.processor.TransactionSettlementProcessor;
import de.flxplzk.frontend.backend.processor.TransactionWorkingTimeProcessor;
import de.flxplzk.frontend.backend.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionService implements Subscribable<Transaction> {
    private final TransactionRepository transactionRepository;
    private final Processor<Transaction, Long> transactionWorkingTimeProcessor = new TransactionWorkingTimeProcessor();
    private final Processor<Transaction, Long> transactionAbsenceProcessor = new TransactionAbsenceProcessor();
    private final Processor<Transaction, Long> transactionSettlementProcessor = new TransactionSettlementProcessor();

    private final List<ServiceListener<Transaction>> listeners = Lists.newArrayList();

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void save(Transaction model) {
        this.calculate(model);
        model.setCreated(LocalDateTime.now());
        this.transactionRepository.save(model);
        notifyChange(model);
    }

    private void notifyChange(Transaction model) {
        this.listeners.forEach(transactionServiceListener -> transactionServiceListener.change(new ChangeEvent<>(model)));
    }

    public List<Transaction> findAll() {
        return Lists.newArrayList(this.transactionRepository.findAll());
    }

    public void delete(Transaction transaction) {
        Transaction savedEntity = this.transactionRepository.findOne(transaction.getId());
        if (BillingStatus.BILLED.equals(savedEntity.getBillingStatus()))
            throw new TransactionAlreadyBilledException("Die Buchung wurde bereits fakturiert und kann deshalb nicht gelöscht oder verändert werden");
        this.transactionRepository.delete(savedEntity);
        this.notifyChange(savedEntity);
    }

    private void calculate(Transaction model) {
        TransactionType transactionType = model.getTransactionType();
        if (TransactionType.SB.equals(transactionType)) {
            model.setAmount(transactionWorkingTimeProcessor.process(model));
        } else if (TransactionType.A.equals(transactionType)) {
            model.setAmount(transactionSettlementProcessor.process(model));
        } else {
            model.setAmount(transactionAbsenceProcessor.process(model));
        }
    }

    @Override
    public Registration addListener(ServiceListener<Transaction> listener) {
        this.listeners.add(listener);
        return () -> this.listeners.remove(listener);
    }
}