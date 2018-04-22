package de.flxplzk.frontend.ui.view.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.frontend.backend.domain.Transaction;
import de.flxplzk.vaadin.common.AbstractViewComponent;
import de.flxplzk.vaadin.mvvm.ListingBound;
import de.flxplzk.vaadin.mvvm.View;
import de.flxplzk.vaadin.mvvm.ViewModelComposer;

/**
 * TransactionView displays everything needed for doing CRUD operations transaction entities.
 *
 * @author flxplzk
 */
@View(model = "transactionViewModel")
@SpringView(name = TransactionView.VIEW_NAME)
public class TransactionView extends AbstractViewComponent {

    public static final String VIEW_NAME = "Transaktionen";

    private final TextField searchTextField = new TextField();

    @ListingBound(to = "transactions")
    private Grid<Transaction> transactionGrid = new Grid<>();

    private final HorizontalLayout searchPanel = new HorizontalLayout(this.searchTextField);
    private final VerticalLayout rootLayout = new VerticalLayout(this.searchPanel, this.transactionGrid);

    public TransactionView(ViewModelComposer viewModelComposer){
        super(viewModelComposer);
        init();
    }

    /**
     * UI initialization has to be done here.
     */
    private void init(){
        setCompositionRoot(this.rootLayout);

        this.searchPanel.setSizeFull();
        this.searchPanel.setMargin(false);

        this.searchTextField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        this.searchTextField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        this.searchTextField.setPlaceholder("Suchkriterium ... ");
        this.searchTextField.setIcon(VaadinIcons.SEARCH);

        this.rootLayout.setSizeFull();
        this.rootLayout.setMargin(true);

        this.transactionGrid.setSizeFull();
        this.transactionGrid
                .addColumn(Transaction::getId)
                .setCaption("ID");
        this.transactionGrid
                .addColumn(transaction -> transaction.getEmployee().getFirstName() + " " + transaction.getEmployee().getLastName())
                .setCaption("Mitarbeiter");
        this.transactionGrid
                .addColumn(Transaction::getTransactionType)
                .setCaption("Buchungsart");
        this.transactionGrid
                .addColumn(Transaction::getTransactionDate)
                .setCaption("Buchungsdatum");
        this.transactionGrid
                .addColumn(transaction -> transaction.getStart())
                .setCaption("Von");
        this.transactionGrid
                .addColumn(transaction -> transaction.getEnd())
                .setCaption("Bis");
        this.transactionGrid
                .addColumn(transaction -> transaction.getMinutesBreak())
                .setCaption("Pause (min)");
        this.transactionGrid
                .addColumn(transaction -> transaction.getAmount())
                .setCaption("Arbeitszeit");
    }

}
