package de.flxplzk.frontend.ui.view.model;

import com.vaadin.data.HasValue;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.frontend.backend.domain.EmployeeProfile;
import de.flxplzk.frontend.backend.service.EmployeeProfileService;
import de.flxplzk.frontend.backend.service.EmployeeService;
import de.flxplzk.frontend.backend.service.Subscribable;
import de.flxplzk.vaadin.mvvm.Property;

import java.util.ArrayList;
import java.util.List;

public class EmployeeViewModel implements HasValue.ValueChangeListener<String>, Subscribable.ServiceListener<Employee> {

    private final EmployeeService employeeService;

    private final Property<String> filterText = new Property<>("");
    private final Property<List<Employee>> employees = new Property<>(new ArrayList<>());

    public EmployeeViewModel(EmployeeService service) {
        this.employeeService = service;
        this.employees.setValue(this.employeeService.findAll());
        this.filterText.addValueChangeListener(this);
        this.employeeService.addListener(this);
    }

    @Override
    public void valueChange(HasValue.ValueChangeEvent<String> valueChangeEvent) {
        this.employees.setValue(this.employeeService.findByFilter(this.filterText.getValue()));
    }

    @Override
    public void change(Subscribable.ChangeEvent<Employee> changeEvent) {
            this.employees.setValue(this.employeeService.findAll());
    }
}
