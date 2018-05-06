package de.flxplzk.frontend.ui.view.components;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.vaadin.common.AbstractViewComponent;
import de.flxplzk.vaadin.mvvm.ItemBound;
import de.flxplzk.vaadin.mvvm.ListingBound;
import de.flxplzk.vaadin.mvvm.View;
import de.flxplzk.vaadin.mvvm.ViewModelComposer;

/**
 * EmployeeView displays everything needed for doing CRUD operations employee entities.
 *
 * @author flxplzk
 */
@View(model = "employeeViewModel")
@SpringView(name = EmployeeView.VIEW_NAME)
public class EmployeeView extends AbstractViewComponent {

    public static final String VIEW_NAME = "Mitarbeiter";

    @ListingBound(to = "employees")
    private final Grid<Employee> employeeGrid = new Grid<>();

    @ItemBound(to = "filterText")
    private final TextField mFilterTextField = new TextField();

    private final HorizontalLayout panel = new HorizontalLayout(this.mFilterTextField);
    private final VerticalLayout rootLayout = new VerticalLayout(this.panel, this.employeeGrid);

    public EmployeeView(ViewModelComposer viewModelComposer) {
        super(viewModelComposer);
        build();
    }

    private void build() {
        this.setCompositionRoot(this.rootLayout);

        this.rootLayout.setMargin(true);
        this.rootLayout.setSizeFull();

        this.panel.setSizeFull();
        this.panel.setMargin(false);

        this.mFilterTextField.setPlaceholder("Suchkriterium ... ");
        this.mFilterTextField.setSizeFull();

        this.employeeGrid.setSizeFull();
        this.employeeGrid.addColumn(Employee::getId).setCaption("ID");
        this.employeeGrid.addColumn(Employee::getFirstName).setCaption("Vorname");
        this.employeeGrid.addColumn(Employee::getLastName).setCaption("Nachname");
        this.employeeGrid.addColumn(Employee::getEmployeeDate).setCaption("Beschäftigt seit");
        this.employeeGrid.addColumn(employee -> employee.getEmployeeProfile().getProfileName()).setCaption("Beschäftigt als");
    }
}
