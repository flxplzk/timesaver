package de.flxplzk.frontend.ui.view.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.frontend.backend.domain.Transaction;
import de.flxplzk.frontend.backend.domain.TransactionType;
import de.flxplzk.frontend.ui.common.NumberTextFieldField;
import de.flxplzk.vaadin.common.AbstractViewComponent;
import de.flxplzk.vaadin.mvvm.*;

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

    @SelectionBound(to = "selectedTransaction")
    @ListingBound(to = "transactions")
    private final Grid<Transaction> transactionGrid = new Grid<>();

    @EnableBound(to = "transactionSelected")
    @OnClick(method = "deleteSelected")
    private final Button deleteSelected = new Button(VaadinIcons.TRASH);

    @ItemBound(to = "selectedEmployee")
    @ListingBound(to = "employees")
    private final ComboBox<Employee> employeeComboBox = new ComboBox<>("Mitarbeiter");

    @ItemBound(to = "transactionDate")
    private final DateField transactionDate = new DateField("Buchungsdatum");

    @ItemBound(to = "selectedType")
    @ListingBound(to = "transactionTypes")
    private final ComboBox<TransactionType> transactionTypeComboBox = new ComboBox<>("Buchungsart");

    @ItemBound(to = "showTimeSelection")
    private final Property<Boolean> showTimeSelection = new Property<>(Boolean.FALSE);

    @ItemBound(to = "createMode")
    private final Property<Boolean> showCrudPanel = new Property<>(Boolean.FALSE);

    @ItemBound(to = "fromDateTime")
    private final DateTimeField fromDateTime = new DateTimeField("Von");

    @ItemBound(to = "toDateTime")
    private final DateTimeField toDateTimeField = new DateTimeField("Bis");

    @ItemBound(to = "breakMinutes")
    private final NumberTextFieldField breakField = new NumberTextFieldField("Pause in Minuten");

    @OnClick(method = "save")
    @EnableBound(to = "enableSave")
    private final Button saveButton = new Button("Speichern");

    @OnClick(method = "reset")
    private final Button cancelButton = new Button("Abrechen");

    @OnClick(method = "add")
    private final Button addTransaction = new Button(VaadinIcons.PLUS_CIRCLE_O);

    private final HorizontalLayout operationPanel = new HorizontalLayout(this.addTransaction, this.deleteSelected);

    private final HorizontalLayout searchPanel = new HorizontalLayout(this.searchTextField, this.operationPanel);

    private final HorizontalLayout firstRowTransactionCrud = new HorizontalLayout(
            this.employeeComboBox,
            this.transactionTypeComboBox,
            this.transactionDate
    );

    private final HorizontalLayout secondRowTransactionCrud = new HorizontalLayout(
            this.fromDateTime,
            this.toDateTimeField,
            this.breakField,
            this.saveButton,
            this.cancelButton
    );

    private final HorizontalLayout crudSaveCanelPanel = new HorizontalLayout(this.saveButton, this.cancelButton);

    private final HorizontalLayout thirdTransactionCrud = new HorizontalLayout(
            this.crudSaveCanelPanel
    );

    private final VerticalLayout transactionCrudPanel = new VerticalLayout(
            this.firstRowTransactionCrud,
            this.secondRowTransactionCrud,
            this.thirdTransactionCrud
    );

    private final VerticalLayout rootLayout = new VerticalLayout(
            this.transactionCrudPanel,
            this.searchPanel,
            this.transactionGrid
    );

    public TransactionView(ViewModelComposer viewModelComposer) {
        super(viewModelComposer);
        init();
    }

    /**
     * UI initialization has to be done here.
     */
    private void init() {
        setCompositionRoot(this.rootLayout);

        this.searchPanel.setSizeFull();
        this.searchPanel.setMargin(false);
        this.searchPanel.setComponentAlignment(this.operationPanel, Alignment.MIDDLE_RIGHT);

        this.operationPanel.setMargin(false);
        this.operationPanel.setSizeUndefined();

        this.deleteSelected.addStyleName(ValoTheme.BUTTON_DANGER);

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
                .addColumn(transaction -> transaction.getTransactionType() + " - " + transaction.getTransactionType().getName())
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
                .addColumn(transaction -> {
                    long hours = transaction.getAmount() / 60;
                    long minutes = transaction.getAmount() % 60;
                    return String.valueOf(hours) + "h:" + String.valueOf(minutes) + "min";
                })
                .setCaption("Arbeitszeit");

        this.firstRowTransactionCrud.setSizeFull();
        this.firstRowTransactionCrud.setMargin(false);

        this.secondRowTransactionCrud.setSizeFull();
        this.secondRowTransactionCrud.setMargin(false);

        this.thirdTransactionCrud.setSizeFull();
        this.thirdTransactionCrud.setMargin(false);
        this.thirdTransactionCrud.setComponentAlignment(this.crudSaveCanelPanel, Alignment.MIDDLE_RIGHT);

        this.transactionCrudPanel.setMargin(false);
        this.transactionCrudPanel.setSizeFull();

        this.addTransaction.addStyleName(ValoTheme.BUTTON_PRIMARY);

        this.showCrudPanel.addValueChangeListener(valueChangeEvent -> this.transactionCrudPanel.setVisible(valueChangeEvent.getValue()));

        this.secondRowTransactionCrud.setVisible(this.showTimeSelection.getValue());
        this.showTimeSelection.addValueChangeListener(changeEvent -> this.secondRowTransactionCrud.setVisible(changeEvent.getValue()));

        this.employeeComboBox.setSizeFull();
        this.transactionDate.setSizeFull();
        this.transactionTypeComboBox.setSizeFull();
        this.fromDateTime.setSizeFull();
        this.toDateTimeField.setSizeFull();
        this.breakField.setSizeFull();
        this.saveButton.setSizeFull();
        this.saveButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        this.cancelButton.setSizeFull();
        this.transactionTypeComboBox.setItemCaptionGenerator(TransactionType::getName);
        this.employeeComboBox.setItemCaptionGenerator(employee -> employee.getFirstName() + " " + employee.getLastName());
        this.showTimeSelection.addValueChangeListener(valueChangeEvent -> {
            this.fromDateTime.setVisible(valueChangeEvent.getValue());
            this.toDateTimeField.setVisible(valueChangeEvent.getValue());
            this.breakField.setVisible(valueChangeEvent.getValue());
        });
    }
}