package de.flxplzk.frontend.backend.service;

import de.flxplzk.frontend.backend.repository.TransactionRepository;

public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
