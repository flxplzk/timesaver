package de.flxplzk.frontend.ui.view.model;

import com.google.common.collect.Lists;
import com.vaadin.shared.Registration;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.UI;
import de.flxplzk.frontend.backend.domain.BillingStatus;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.frontend.backend.domain.Transaction;
import de.flxplzk.frontend.backend.domain.TransactionType;
import de.flxplzk.frontend.backend.service.EmployeeService;
import de.flxplzk.frontend.backend.service.TransactionService;
import de.flxplzk.vaadin.common.AsyncTask;
import de.flxplzk.vaadin.common.ConfirmationDialog;
import de.flxplzk.vaadin.common.NotificationManager;
import de.flxplzk.vaadin.mvvm.Property;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionViewModel {

    private static final Employee DEFAULT_EMPLOYEE = new Employee("", "");
    private static final LocalDate DEFAULT_DATE = LocalDate.now();
    private static final LocalDateTime FROM_DATE_TIME = DEFAULT_DATE.atStartOfDay();
    private static final LocalDateTime TO_DATE_TIME = DEFAULT_DATE.atStartOfDay();

    private final TransactionService transactionService;
    private final EmployeeService employeeService;
    private final NotificationManager notificationManager;
    private final List<Registration> registrations = Lists.newArrayList();
    private final UI currentUI;

    // listing properties
    private final Property<Transaction> selectedTransaction = new Property<>(new Transaction());
    private final Property<List<Transaction>> transactions = new Property<>(Lists.newArrayList());
    private final Property<Boolean> transactionSelected = new Property<>(Boolean.FALSE);

    // crud properties
    private final Property<Employee> selectedEmployee = new Property<>(DEFAULT_EMPLOYEE);
    private final Property<List<Employee>> employees = new Property<>(Lists.newArrayList());
    private final Property<LocalDate> transactionDate = new Property<>(DEFAULT_DATE);
    private final Property<TransactionType> selectedType = new Property<>(TransactionType.SB);
    private final Property<List<TransactionType>> transactionTypes = new Property<>(Lists.newArrayList(TransactionType.values()));
    private final Property<LocalDateTime> fromDateTime = new Property<>(FROM_DATE_TIME);
    private final Property<LocalDateTime> toDateTime = new Property<>(TO_DATE_TIME);
    private final Property<String> breakMinutes = new Property<>(String.valueOf(0));
    private final Property<Boolean> enableSave = new Property<>(Boolean.FALSE);
    private final Property<Boolean> showTimeSelection = new Property<>(Boolean.TRUE);
    private final Property<Boolean> createMode = new Property<>(Boolean.FALSE);

    public TransactionViewModel(TransactionService transactionService, EmployeeService employeeService, NotificationManager notificationManager, UI currentUI) {
        this.transactionService = transactionService;
        this.employeeService = employeeService;
        this.notificationManager = notificationManager;
        this.currentUI = currentUI;
        this.init();
    }

    private void init() {
        this.registrations.add(this.selectedEmployee.addValueChangeListener(valueChangeEvent -> enableSave()));
        this.registrations.add(this.fromDateTime.addValueChangeListener(valueChangeEvent -> enableSave()));
        this.registrations.add(this.toDateTime.addValueChangeListener(valueChangeEvent -> enableSave()));
        this.registrations.add(this.selectedType.addValueChangeListener(valueChangeEvent -> this.showTimeSelection.setValue(TransactionType.SB.equals(valueChangeEvent.getValue()))));
        new FetchTransActionsTask().execute();
        this.employees.setValue(employeeService.findAll());
        this.transactionService.addListener(changeEvent -> new FetchTransActionsTask().execute());
        this.selectedTransaction.addValueChangeListener(changeEvent -> {
            if (changeEvent.getValue() != null) {
                this.transactionSelected.setValue(Boolean.TRUE);
            } else {
                this.transactionSelected.setValue(Boolean.FALSE);
            }
        });
    }

    public void deleteSelected() {
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.confirm("Wollen Sie die Buchung wirklich löschen?", this.currentUI, this::handleConfirmationResponse);
        this.transactionSelected.setValue(Boolean.FALSE);
    }

    private void handleConfirmationResponse(ConfirmationDialog.ConfirmationCallback.ResponseType responseType) {
        if (ConfirmationDialog.ConfirmationCallback.ResponseType.OK.equals(responseType))
            new DeleteTask(selectedTransaction.getValue()).execute();
    }

    private void enableSave() {
        if ((!FROM_DATE_TIME.equals(this.fromDateTime.getValue())
                && !TO_DATE_TIME.equals(this.toDateTime.getValue()))
                && TransactionType.SB.equals(this.selectedType.getValue())
                && !DEFAULT_EMPLOYEE.equals(this.selectedEmployee.getValue())) {
            this.enableSave.setValue(Boolean.TRUE);
        } else if ((!TransactionType.SB.equals(this.selectedType.getValue())
                && !DEFAULT_EMPLOYEE.equals(this.selectedEmployee.getValue()))) {
            this.enableSave.setValue(Boolean.TRUE);
        } else {
            this.enableSave.setValue(Boolean.FALSE);
        }
    }

    public void save() {
        new SaveTask().execute();
    }

    public void reset() {
        this.selectedEmployee.setValue(DEFAULT_EMPLOYEE);
        this.selectedType.setValue(TransactionType.A);
        this.fromDateTime.setValue(FROM_DATE_TIME);
        this.toDateTime.setValue(TO_DATE_TIME);
        this.breakMinutes.setValue("0");
        this.createMode.setValue(Boolean.FALSE);
    }

    public void add() {
        this.createMode.setValue(Boolean.TRUE);
    }

    class SaveTask extends AsyncTask<Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Transaction model = new Transaction();
            model.setEmployee(selectedEmployee.getValue());
            model.setTransactionDate(transactionDate.getValue());
            model.setTransactionType(selectedType.getValue());
            model.setStart(fromDateTime.getValue());
            model.setEnd(toDateTime.getValue());
            model.setMinutesBreak(Integer.valueOf(breakMinutes.getValue()));
            model.setBillingStatus(BillingStatus.NOT_BILLED);
            transactionService.save(model);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            notificationManager.showNotification("Buchung wurde gespeichert", NotificationManager.NotificationStyle.SUCCESS);
            reset();
        }

        @Override
        protected void onError(RuntimeException cause) {
            notificationManager.showNotification("Beim speichern der Buchung ist folgender Fehler aufgetreten: " + cause.getMessage(), NotificationManager.NotificationStyle.ERROR);
            reset();
        }
    }

    class FetchTransActionsTask extends AsyncTask<Void, List<Transaction>> {

        @Override
        protected List<Transaction> doInBackground(Void... params) {
            return transactionService.findAll();
        }

        @Override
        protected void onPostExecute(List<Transaction> result) {
            transactions.setValue(result);
        }
    }

    private class DeleteTask extends AsyncTask<Void, Void> {
        private final Transaction transaction;

        private DeleteTask(Transaction transaction) {
            this.transaction = transaction;
        }

        @Override
        protected Void doInBackground(Void... params) {
            transactionService.delete(this.transaction);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            notificationManager.showNotification("Die Buchung wurde erfolgreich gelöscht", NotificationManager.NotificationStyle.SUCCESS);
        }

        @Override
        protected void onError(RuntimeException cause) {
            notificationManager.showNotification(cause.getMessage(), NotificationManager.NotificationStyle.FAILURE);
        }
    }
}
