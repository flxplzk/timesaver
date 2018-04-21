package de.flxplzk.frontend.ui.components.report;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.vaadin.mvvm.Property;


import java.util.ArrayList;
import java.util.List;

public class ReportViewModel {

    private Property<String> firstName = new Property<>("");
    private Property<List<Employee>> employees = new Property<>(new ArrayList<>());

    private Property<Employee> selectedEmployee = new Property<>(new Employee());

    public ReportViewModel() {
        firstName.setValue("Phillip");
        Employee employee = new Employee();
        employee.setFirstName("Hans");
        List<Employee> strings = new ArrayList<>();
        strings.add(employee);
        employee = new Employee();
        employee.setFirstName("Torsten");
        strings.add(employee);
        employees.setValue(strings);
        selectedEmployee.setValue(employee);
    }

    public void setFirstNameToDefault(Button.ClickEvent clickEvent){
        firstName.setValue("Hans");
    }

    public void selectItem(Grid.ItemClick<String> itemClickEvent){
        this.firstName.setValue(itemClickEvent.getItem());
    }
}
