package de.flxplzk.frontend.ui.components.employee;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.Registration;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.vaadin.mvvm.*;
import de.flxplzk.frontend.ui.components.menu.MenuItem;
import de.flxplzk.frontend.ui.components.menu.MenuItemProvider;

/**
 * EmployeeView displays everything needed for doing CRUD operations employee entities.
 *
 * @author flxplzk
 */
@View(model = "employeeViewModel")
@SpringView(name = EmployeeView.VIEW_NAME)
public class EmployeeView extends VerticalLayout implements MenuItemProvider{

    public static final String VIEW_NAME = "Mitarbeiter";

    private final ViewModelComposer viewModelComposer;

    private final Label title_h1 = new Label("Mitarbeiterdaten Verwalten");

    @ListingBound(to = "employees")
    private final Grid<Employee> employeeGrid = new Grid<>();

    @OnClick(method = "setEmptyEmployee")
    private final Button newEmployeeButton = new Button("Mitarbeiter anlegen", VaadinIcons.PLUS_CIRCLE);

    @OnClick(method = "setEmptyEmployeeProfile")
    private final Button newEmployeeProfileButton = new Button("Mitarbeiterprofil anlegen", VaadinIcons.PLUS_CIRCLE);

    @ItemBound(to = "filterText")
    private final TextField mFilterTextField = new TextField();
    private Registration registration;

    public EmployeeView(ViewModelComposer viewModelComposer){
        this.viewModelComposer = viewModelComposer;
        build();
    }

    private void build() {
        this.title_h1.setStyleName(ValoTheme.LABEL_H1);
        this.newEmployeeButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        HorizontalLayout panel = new HorizontalLayout();
        panel.addComponents(this.mFilterTextField, this.newEmployeeProfileButton, this.newEmployeeButton);
        this.employeeGrid.setSizeFull();
        this.addComponents(this.title_h1, panel,this.employeeGrid);
        panel.setSizeFull();
        this.mFilterTextField.setPlaceholder("Suchkriterium ... ");
        this.mFilterTextField.setSizeFull();
        this.employeeGrid.addColumn(Employee::getId).setCaption("ID");
        this.employeeGrid.addColumn(Employee::getFirstName).setCaption("Vorname");
        this.employeeGrid.addColumn(Employee::getLastName).setCaption("Nachname");
        this.employeeGrid.addColumn(Employee::getEmployeeDate).setCaption("Beschäftigt seit");
        this.employeeGrid.addColumn(employee -> employee.getEmployeeProfile().getProfileName()).setCaption("Beschäftigt als");
        this.newEmployeeButton
                .addClickListener(event ->
                        getUI().addWindow(new CrudEmployeeView(this.viewModelComposer)
                        ));
        this.newEmployeeProfileButton
                .addClickListener(event ->
                        getUI().addWindow(new CrudEmployeeProfileView(this.viewModelComposer)
                        ));
    }

    @Override
    public MenuItem getMenuItem() {
        return new MenuItem(VIEW_NAME, FontAwesome.USER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.registration = this.viewModelComposer.bind(this);
    }

    @Override
    public void beforeLeave(ViewBeforeLeaveEvent event) {
        this.registration.remove();
        event.navigate();
    }
}
