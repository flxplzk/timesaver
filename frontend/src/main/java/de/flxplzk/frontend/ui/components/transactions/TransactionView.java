package de.flxplzk.frontend.ui.components.transactions;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.frontend.backend.domain.Transaction;
import de.flxplzk.frontend.ui.components.menu.MenuItem;
import de.flxplzk.frontend.ui.components.menu.MenuItemProvider;
import de.flxplzk.vaadin.mvvm.ListingBound;
import de.flxplzk.vaadin.mvvm.OnClick;
import de.flxplzk.vaadin.mvvm.View;

/**
 * TransactionView displays everything needed for doing CRUD operations transaction entities.
 *
 * @author flxplzk
 */
@View(model = "transactionViewModel")
@SpringView(name = TransactionView.VIEW_NAME)
public class TransactionView extends VerticalLayout implements MenuItemProvider{

    static final String VIEW_NAME = "Transaktionen";
    private Label greeting = new Label("Transaktionen");
    private Label text = new Label("Transaktionen wie Urlaub, Arbeitszeit, Ausgelichsbuchungen oder Abwesen heiten wie Krankheit oder Theorie können hier gebucht werden");

    @ListingBound(to = "transactions")
    private Grid<Transaction> transactionGrid = new Grid<>();

    @OnClick(method = "prepareEmptyTransaction")
    private Button newTransactionButton = new Button("Transaktion", VaadinIcons.PLUS_CIRCLE);

    public TransactionView(){
        init();
    }

    /**
     * UI initialization has to be done here.
     */
    public void init(){
        greeting.setStyleName(ValoTheme.LABEL_H1);
        text.setStyleName(ValoTheme.LABEL_LIGHT);
        this.addComponents(greeting, text, newTransactionButton, transactionGrid);
        this.transactionGrid.setSizeFull();
    }

    @Override
    public MenuItem getMenuItem() {
        return new MenuItem(TransactionView.VIEW_NAME, FontAwesome.REORDER);
    }
}
