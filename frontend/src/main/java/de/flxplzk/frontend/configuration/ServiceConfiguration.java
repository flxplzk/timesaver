package de.flxplzk.frontend.configuration;

import de.flxplzk.frontend.backend.domain.Employee;
import de.flxplzk.frontend.backend.domain.EmployeeProfile;
import de.flxplzk.frontend.backend.repository.EmployeeProfileRepository;
import de.flxplzk.frontend.backend.repository.EmployeeRepository;
import de.flxplzk.frontend.backend.repository.TransactionRepository;
import de.flxplzk.frontend.backend.service.EmployeeProfileService;
import de.flxplzk.frontend.backend.service.EmployeeService;
import de.flxplzk.frontend.backend.service.TransactionService;
import de.flxplzk.frontend.backend.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    @Bean
    public UserService userService(){
        return new UserService();
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

    @Bean
    public TransactionService transactionService(TransactionRepository transactionRepository) {
        return new TransactionService(transactionRepository);
    }


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
