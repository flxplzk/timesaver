package de.flxplzk.frontend.backend.repository;

import de.flxplzk.frontend.backend.domain.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> { }
