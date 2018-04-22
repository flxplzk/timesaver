package de.flxplzk.frontend.ui.view.model;

import com.vaadin.data.HasValue;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.frontend.backend.domain.EmployeeProfile;
import de.flxplzk.frontend.backend.service.EmployeeProfileService;
import de.flxplzk.frontend.backend.service.EmployeeService;
import de.flxplzk.frontend.backend.service.Subscribable;
import de.flxplzk.vaadin.mvvm.Property;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CrudEmployeeViewModel implements HasValue.ValueChangeListener<Employee>{

    private final EmployeeService service;
    private final EmployeeProfileService employeeProfileService;
    private Subscribable.Registration registration;

    private final Property<Employee> model = new Property<>(new Employee());
    private final Property<String> firstName = new Property<>("");
    private final Property<String> lastName = new Property<>("");
    private final Property<LocalDate> employeeDate = new Property<>(LocalDate.now());
    private final Property<List<EmployeeProfile>> profiles = new Property<>(new ArrayList<>());
    private final Property<EmployeeProfile> selectedProfile = new Property<>(new EmployeeProfile());

    public CrudEmployeeViewModel(EmployeeService employeeService, EmployeeProfileService employeeProfileService) {
        this.service = employeeService;
        this.employeeProfileService = employeeProfileService;
        this.profiles.setValue(this.employeeProfileService.findAll());
        this.model.addValueChangeListener(this);
        this.registration = this.employeeProfileService.addListener(new EmployeeServiceListener());
    }

    public void setModel(Employee model) {
        this.model.setValue(model);
    }

    @Override
    public void valueChange(HasValue.ValueChangeEvent<Employee> event) {
        if (this.model.getValue().getFirstName() == null) {
            this.lastName.setValue("");
        } else {
            this.firstName.setValue(this.model.getValue().getFirstName());
        }

        if (this.model.getValue().getLastName() == null) {
            this.lastName.setValue("");
        } else {
            this.lastName.setValue(this.model.getValue().getLastName());
        }

        if (!(this.model.getValue().getEmployeeDate() == null)) {
            this.employeeDate.setValue(this.model.getValue().getEmployeeDate());
        }

        if (!(this.model.getValue().getEmployeeProfile() == null)) {
            this.selectedProfile.setValue(this.model.getValue().getEmployeeProfile());
        }
    }

    public void save(){
        Employee employee = this.model.getValue();
        employee.setFirstName(this.firstName.getValue());
        employee.setLastName(this.lastName.getValue());
        employee.setEmployeeProfile(this.selectedProfile.getValue());
        employee.setEmployeeDate(this.employeeDate.getValue());
        this.service.save(employee);
        this.model.setValue(new Employee());
    }

    private class EmployeeServiceListener implements Subscribable.ServiceListener<EmployeeProfile> {

        @Override
        public void change(Subscribable.ChangeEvent<EmployeeProfile> changeEvent) {
            profiles.setValue(employeeProfileService.findAll());
        }
    }
}
