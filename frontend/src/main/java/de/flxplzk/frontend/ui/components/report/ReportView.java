package de.flxplzk.frontend.ui.components.report;

import com.vaadin.navigator.ViewBeforeLeaveEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.Registration;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.frontend.ui.components.menu.MenuItem;
import de.flxplzk.frontend.ui.components.menu.MenuItemProvider;
import de.flxplzk.vaadin.mvvm.*;


/**
 * ReportView displays everything needed for doing CRUD operations Reports entities.
 *
 * @author flxplzk
 */
@View(model = "reportViewModel")
@SpringView(name = ReportView.VIEW_NAME)
public class ReportView extends VerticalLayout implements MenuItemProvider {

    static final String VIEW_NAME = "reports";
    private ViewModelComposer viewModelComposer;

    @ItemBound(to = "firstName")
    private TextField greeting = new TextField();

    @ItemBound(to = "firstName")
    private TextField firstNameTextField = new TextField();

    @OnComponentEvent(ofType = Button.ClickEvent.class, method = "setFirstNameToDefault")
    private Button testButton = new Button("click me");

    @ListingBound(to = "employees")
    private Grid<Employee> grid = new Grid<>();

    @ItemBound(to = "selectedEmployee")
    @ListingBound(to = "employees")
    private ComboBox<Employee> comboBox = new ComboBox<>();

    @ItemBound(to = "selectedEmployee")
    @ListingBound(to = "employees")
    private ComboBox<Employee> comboBoxCopy = new ComboBox<>();
    private Registration registration;

    public ReportView(ViewModelComposer viewModelComposer) {
        this.viewModelComposer = viewModelComposer;
        init();
    }

    /**
     * UI initialization has to be done here.
     */
    public void init() {
        greeting.setStyleName(ValoTheme.LABEL_H1);
        this.addComponents(greeting, firstNameTextField, testButton, this.grid, this.comboBox, this.comboBoxCopy);
        this.grid.addColumn(Employee::getFirstName).setCaption("Name");
        this.comboBox.setItemCaptionGenerator(Employee::getFirstName);
        this.comboBoxCopy.setItemCaptionGenerator(Employee::getFirstName);
    }

    @Override
    public MenuItem getMenuItem() {
        return new MenuItem("reports", FontAwesome.REPEAT);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.registration = viewModelComposer.bind(this);
    }

    @Override
    public void beforeLeave(ViewBeforeLeaveEvent event) {
        this.registration.remove();
        event.navigate();
    }
}
