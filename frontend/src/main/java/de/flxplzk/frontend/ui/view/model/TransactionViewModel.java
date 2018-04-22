package de.flxplzk.frontend.ui.view.model;

import com.google.common.collect.Lists;
import de.flxplzk.frontend.backend.domain.Transaction;
import de.flxplzk.frontend.backend.service.EmployeeProfileService;
import de.flxplzk.frontend.backend.service.EmployeeService;
import de.flxplzk.frontend.backend.service.TransactionService;
import de.flxplzk.vaadin.mvvm.Property;

import java.util.List;

public class TransactionViewModel {
    private final EmployeeProfileService employeeProfileService;
    private final EmployeeService employeeService;
    private final TransactionService transactionService;

    private final Property<List<Transaction>> transactions = new Property<>(Lists.newArrayList());

    public TransactionViewModel(EmployeeProfileService employeeProfileService, EmployeeService employeeService, TransactionService transactionService) {
        this.employeeProfileService = employeeProfileService;
        this.employeeService = employeeService;
        this.transactionService = transactionService;
    }
}
