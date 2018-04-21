package de.flxplzk.frontend.configuration;


import com.vaadin.spring.annotation.UIScope;
import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.frontend.backend.domain.EmployeeProfile;
import de.flxplzk.frontend.backend.repository.EmployeeProfileRepository;
import de.flxplzk.frontend.backend.repository.EmployeeRepository;
import de.flxplzk.frontend.backend.service.EmployeeProfileService;
import de.flxplzk.frontend.backend.service.EmployeeService;
import de.flxplzk.frontend.backend.service.UserService;
import de.flxplzk.frontend.ui.common.event.TimeSaverEventBus;
import de.flxplzk.frontend.ui.components.employee.CrudEmployeeProfileViewModel;
import de.flxplzk.frontend.ui.components.employee.CrudEmployeeViewModel;
import de.flxplzk.frontend.ui.components.employee.EmployeeViewModel;
import de.flxplzk.frontend.ui.components.report.ReportViewModel;
import de.flxplzk.vaadin.mvvm.ViewModelComposer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VaadinConfig {

    // ############################# application UIScope ###################################

    @Bean
    @UIScope
    public ViewModelComposer viewModelComposer(ApplicationContext applicationContext){
        return new ViewModelComposer(applicationContext);
    }

    @Bean
    @UIScope
    public TimeSaverEventBus timeSaverEventBus(){
        return new TimeSaverEventBus();
    }
    // ############################# user ###################################

    @Bean
    public UserService userService(){
        return new UserService();
    }

    // ############################# employee ###################################

    @Bean
    @UIScope
    public EmployeeViewModel employeeViewModel(EmployeeProfileService employeeProfileService, EmployeeService employeeService, CrudEmployeeViewModel crudEmployeeViewModel, CrudEmployeeProfileViewModel crudEmployeeProfileViewModel){
        return new EmployeeViewModel(employeeProfileService, employeeService, crudEmployeeViewModel, crudEmployeeProfileViewModel);
    }

    @Bean
    @UIScope
    public CrudEmployeeViewModel crudEmployeeViewModel(EmployeeService employeeService, EmployeeProfileService employeeProfileService){
        return new CrudEmployeeViewModel(employeeService, employeeProfileService);
    }

    @Bean
    @UIScope
    public CrudEmployeeProfileViewModel crudEmployeeProfileViewModel(EmployeeProfileService employeeProfileService){
        return new CrudEmployeeProfileViewModel(employeeProfileService);
    }

    @Bean
    public EmployeeService employeeService(EmployeeRepository employeeRepository, EmployeeProfileService employeeProfileService){
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        generateEmployeeFakeData(employeeProfileService, employeeService);
        return employeeService;
    }

    @Bean
    public EmployeeProfileService employeeProfileService(EmployeeProfileRepository employeeProfileRepository){
        return new EmployeeProfileService(employeeProfileRepository);
    }

    // ############################# report ###################################

    @Bean
    public ReportViewModel reportViewModel(){
        return new ReportViewModel();
    }

    // ############################# transaction ###################################


    // ############################# employee ###################################

    public void generateEmployeeFakeData(EmployeeProfileService employeeProfileService, EmployeeService employeeService){
        EmployeeProfile employeeProfile = new EmployeeProfile();
        employeeProfile.setPermanentEmployed(true);
        employeeProfile.setProfileName("Festangestellt 45");
        employeeProfile.setYearlyHoliday(25);
        employeeProfile.setWeeklyHours(45);
        employeeProfileService.save(employeeProfile);
        employeeProfile = new EmployeeProfile();
        employeeProfile.setPermanentEmployed(false);
        employeeProfile.setProfileName("Minijobber");
        employeeProfile.setYearlyHoliday(0);
        employeeProfile.setWeeklyHours(15);
        employeeProfileService.save(employeeProfile);
        Employee employee = new Employee();
        employee.setFirstName("Felix");
        employee.setLastName("Plazek");
        employee.setHolidayAccount(0);
        employee.setHourAccount(0);
        employee.setEmployeeProfile(employeeProfileService.findAll().get(0));
        employeeService.save(employee);
        employee = new Employee();
        employee.setFirstName("Isabel");
        employee.setLastName("Lorenz");
        employee.setHolidayAccount(0);
        employee.setHourAccount(0);
        employee.setEmployeeProfile(employeeProfileService.findAll().get(1));
        employeeService.save(employee);
    }
}
