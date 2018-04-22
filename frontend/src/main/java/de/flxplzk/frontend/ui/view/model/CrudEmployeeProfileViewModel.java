package de.flxplzk.frontend.ui.view.model;

import com.vaadin.data.HasValue;
import de.flxplzk.frontend.backend.domain.EmployeeProfile;
import de.flxplzk.frontend.backend.service.EmployeeProfileService;
import de.flxplzk.vaadin.mvvm.Property;


public class CrudEmployeeProfileViewModel implements HasValue.ValueChangeListener<EmployeeProfile>{

    private final EmployeeProfileService employeeProfileService;
    private final Property<EmployeeProfile> model = new Property<>(new EmployeeProfile());

    private final Property<String> profileName = new Property<>("");
    private final Property<Boolean> isPermanentEmployed = new Property<>(false);
    private final Property<String> weeklyHours = new Property<>("0");
    private final Property<String> yearlyHoliday = new Property<>("0");

    public CrudEmployeeProfileViewModel(EmployeeProfileService employeeProfileService) {
        this.employeeProfileService = employeeProfileService;
        this.model.addValueChangeListener(this);
    }

    public void setModel(EmployeeProfile model) {
        this.model.setValue(model);
    }

    public void save(){
        EmployeeProfile employeeProfile = this.model.getValue();
        employeeProfile.setProfileName(this.profileName.getValue());
        employeeProfile.setWeeklyHours(Long.valueOf(this.weeklyHours.getValue()));
        employeeProfile.setYearlyHoliday(Long.valueOf(this.yearlyHoliday.getValue()));
        employeeProfile.setPermanentEmployed(this.isPermanentEmployed.getValue());
        this.employeeProfileService.save(employeeProfile);
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
}
