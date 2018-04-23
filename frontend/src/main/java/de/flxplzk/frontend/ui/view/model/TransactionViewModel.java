package de.flxplzk.frontend.ui.view.model;

import com.google.common.collect.Lists;
import de.flxplzk.frontend.backend.domain.Transaction;
import de.flxplzk.frontend.backend.service.EmployeeProfileService;
import de.flxplzk.frontend.backend.service.EmployeeService;
import de.flxplzk.frontend.backend.service.Subscribable;
import de.flxplzk.frontend.backend.service.TransactionService;
import de.flxplzk.vaadin.common.AsyncTask;
import de.flxplzk.vaadin.mvvm.Property;

import java.util.List;

public class TransactionViewModel {
    private final TransactionService transactionService;

    private final Property<List<Transaction>> transactions = new Property<>(Lists.newArrayList());

    public TransactionViewModel(TransactionService transactionService) {
        this.transactionService = transactionService;
        new FetchTransActionsTask().execute();
        this.transactionService.addListener(changeEvent -> new FetchTransActionsTask().execute());
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
}
