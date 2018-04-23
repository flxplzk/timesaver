package de.flxplzk.frontend.ui.view.model;

import com.vaadin.data.HasValue;
import com.vaadin.spring.navigator.SpringNavigator;
import de.flxplzk.frontend.backend.domain.EmployeeProfile;
import de.flxplzk.frontend.backend.service.EmployeeProfileService;
import de.flxplzk.vaadin.common.AsyncTask;
import de.flxplzk.vaadin.common.NotificationManager;
import de.flxplzk.vaadin.mvvm.Property;


public class CrudEmployeeProfileViewModel implements HasValue.ValueChangeListener<EmployeeProfile>{

    private final EmployeeProfileService employeeProfileService;
    private final SpringNavigator navigator;
    private final Property<EmployeeProfile> model = new Property<>(new EmployeeProfile());

    private final Property<String> profileName = new Property<>("");
    private final Property<Boolean> isPermanentEmployed = new Property<>(false);
    private final Property<String> weeklyHours = new Property<>("0");
    private final Property<String> yearlyHoliday = new Property<>("0");
    private final NotificationManager notificationManager;

    public CrudEmployeeProfileViewModel(EmployeeProfileService employeeProfileService, SpringNavigator navigator, NotificationManager notificationManager) {
        this.employeeProfileService = employeeProfileService;
        this.navigator = navigator;
        this.notificationManager = notificationManager;
        this.model.addValueChangeListener(this);
    }

    public void setModel(EmployeeProfile model) {
        this.model.setValue(model);
    }

    public void save(){
        new SaveTask().execute();
        this.navigate();
    }

    public void cancel() {
        this.navigate();
    }

    private void navigate() {
        String state = navigator.getState();
        navigator.navigateTo(state);
    }

    @Override
    public void valueChange(HasValue.ValueChangeEvent<EmployeeProfile> event) {
        if (this.model.getValue().getProfileName() == null){
            this.profileName.setValue("");
        }
        this.isPermanentEmployed.setValue(this.model.getValue().isPermanentEmployed());
        this.weeklyHours.setValue(String.valueOf(this.model.getValue().getWeeklyHours()));
        this.yearlyHoliday.setValue(String.valueOf(this.model.getValue().getYearlyHoliday()));
    }

    class SaveTask extends AsyncTask<Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            EmployeeProfile employeeProfile = model.getValue();
            employeeProfile.setProfileName(profileName.getValue());
            employeeProfile.setWeeklyHours(Long.valueOf(weeklyHours.getValue()));
            employeeProfile.setYearlyHoliday(Long.valueOf(yearlyHoliday.getValue()));
            employeeProfile.setPermanentEmployed(isPermanentEmployed.getValue());
            employeeProfileService.save(employeeProfile);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            notificationManager.showNotification("Mitarbeiterprofil wurde gespeichert", NotificationManager.NotificationStyle.SUCCESS);
        }
    }
}
