package de.flxplzk.frontend.ui.view.model;

import com.vaadin.data.HasValue;
import com.vaadin.spring.navigator.SpringNavigator;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.frontend.backend.domain.EmployeeProfile;
import de.flxplzk.frontend.backend.service.EmployeeProfileService;
import de.flxplzk.frontend.backend.service.EmployeeService;
import de.flxplzk.frontend.backend.service.Subscribable;
import de.flxplzk.vaadin.common.AsyncTask;
import de.flxplzk.vaadin.common.NotificationManager;
import de.flxplzk.vaadin.mvvm.Property;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CrudEmployeeViewModel implements HasValue.ValueChangeListener<Employee> {

    private final EmployeeService service;
    private final EmployeeProfileService employeeProfileService;
    private final SpringNavigator navigator;
    private Subscribable.Registration registration;

    private final Property<Employee> model = new Property<>(new Employee());
    private final Property<String> firstName = new Property<>("");
    private final Property<String> lastName = new Property<>("");
    private final Property<LocalDate> employeeDate = new Property<>(LocalDate.now());
    private final Property<List<EmployeeProfile>> profiles = new Property<>(new ArrayList<>());
    private final Property<EmployeeProfile> selectedProfile = new Property<>(new EmployeeProfile());
    private final NotificationManager notificationManager;

    public CrudEmployeeViewModel(EmployeeService employeeService, EmployeeProfileService employeeProfileService, SpringNavigator springNavigator, NotificationManager notificationManager) {
        this.service = employeeService;
        this.employeeProfileService = employeeProfileService;
        this.notificationManager = notificationManager;
        this.profiles.setValue(this.employeeProfileService.findAll());
        this.model.addValueChangeListener(this);
        this.navigator = springNavigator;
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

    public void save() {
        new SaveTask().execute();
        this.navigate();
    }

    public void cancel() {
        this.navigate();
    }

    private void navigate() {
        String state = navigator.getState();
        navigator.navigateTo(state);
        if (this.registration != null) {
            this.registration.remove();
            this.registration = null;
        }
    }

    private class EmployeeServiceListener implements Subscribable.ServiceListener<EmployeeProfile> {

        @Override
        public void change(Subscribable.ChangeEvent<EmployeeProfile> changeEvent) {
            profiles.setValue(employeeProfileService.findAll());
        }
    }

    class SaveTask extends AsyncTask<Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Employee employee = model.getValue();
            employee.setFirstName(firstName.getValue());
            employee.setLastName(lastName.getValue());
            employee.setEmployeeProfile(selectedProfile.getValue());
            employee.setEmployeeDate(employeeDate.getValue());
            service.save(employee);
            model.setValue(new Employee());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            notificationManager.showNotification("Mitaberterdaten wurden gespeichert", NotificationManager.NotificationStyle.SUCCESS);
        }
    }
}
