package de.flxplzk.frontend.ui.view.components;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.frontend.backend.domain.TransactionType;
import de.flxplzk.frontend.ui.common.NumberTextFieldField;
import de.flxplzk.vaadin.common.AbstractViewComponent;
import de.flxplzk.vaadin.mvvm.*;

@View(model = "crudTransactionViewModel")
public class CrudTransactionViewComponent extends AbstractViewComponent {

    @ItemBound(to = "selectedEmployee")
    @ListingBound(to = "employees")
    private final ComboBox<Employee> employeeComboBox = new ComboBox<>("Mitarbeiter");

    @ItemBound(to = "transactionDate")
    private final DateField transactionDate = new DateField("Buchungsdatum");

    @ItemBound(to = "selectedType")
    @ListingBound(to = "transactionTypes")
    private final ComboBox<TransactionType> transactionTypeComboBox = new ComboBox<>("Buchungsart");

    @ItemBound(to = "fromDateTime")
    private final DateTimeField fromDateTime = new DateTimeField("Von");

    @ItemBound(to = "toDateTime")
    private final DateTimeField toDateTimeField = new DateTimeField("Bis");

    @ItemBound(to = "breakMinutes")
    private final NumberTextFieldField breakField = new NumberTextFieldField("Pause in Minuten");

    @OnClick(method = "save")
    @EnableBound(to = "enableSave")
    private final Button saveButton = new Button("Speichern");

    @OnClick(method = "cancel")
    private final Button cancelButton = new Button("Abrechen");

    private final VerticalLayout rootLayout = new VerticalLayout(
            this.employeeComboBox,
            this.transactionDate,
            this.transactionTypeComboBox,
            this.fromDateTime,
            this.toDateTimeField,
            this.breakField,
            this.saveButton,
            this.cancelButton
    );

    public CrudTransactionViewComponent(ViewModelComposer viewModelComposer) {
        super(viewModelComposer);
        this.setCompositionRoot(this.rootLayout);
        this.build();
    }

    private void build() {
        this.rootLayout.setSizeFull();
        this.rootLayout.setMargin(true);
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
    }
}