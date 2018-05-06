package de.flxplzk.frontend.backend.repository;

import de.flxplzk.frontend.backend.domain.Transaction;
import de.flxplzk.frontend.backend.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


public interface TransactionRepository extends CrudRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    @Query("SELECT t from Transaction t where :from <= t.transactionDate and t.transactionDate <= :to and t.transactionType = :type")
    List<Transaction> findAllByTransactionDateAfterAndTransactionDateBeforeAndTransactionTypeEquals(
            @Param(value = "from") LocalDate from,
            @Param(value = "to") LocalDate to,
            @Param(value = "type") TransactionType type
    );
}
