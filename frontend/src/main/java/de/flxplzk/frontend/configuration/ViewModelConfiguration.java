package de.flxplzk.frontend.configuration;

import com.vaadin.spring.annotation.UIScope;
import de.flxplzk.frontend.backend.service.EmployeeProfileService;
import de.flxplzk.frontend.backend.service.EmployeeService;
import de.flxplzk.frontend.backend.service.TransactionService;
import de.flxplzk.frontend.ui.TimeSaverUI;
import de.flxplzk.frontend.ui.view.model.*;
import de.flxplzk.vaadin.common.MenuItem;
import de.flxplzk.vaadin.common.WindowManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Collection;

@Configuration
public class ViewModelConfiguration {

    @Bean
    @UIScope
    public MainViewModel mainViewModel(WindowManager windowManager, Collection<MenuItem> items, TimeSaverUI timeSaverUI) {
        return new MainViewModel(windowManager, items, timeSaverUI);
    }

    @Bean
    @UIScope
    public EmployeeViewModel employeeViewModel(EmployeeProfileService employeeProfileService, EmployeeService employeeService, CrudEmployeeViewModel crudEmployeeViewModel, CrudEmployeeProfileViewModel crudEmployeeProfileViewModel){
        return new EmployeeViewModel(employeeProfileService, employeeService, crudEmployeeViewModel, crudEmployeeProfileViewModel);
    }

    @Bean
    @UIScope
    public TransactionViewModel transactionViewModel(EmployeeProfileService employeeProfileService, EmployeeService employeeService, TransactionService transactionService){
        return new TransactionViewModel(employeeProfileService, employeeService, transactionService);
    }

    @Bean
    @Scope(value = "prototype")
    public CrudEmployeeViewModel crudEmployeeViewModel(EmployeeService employeeService, EmployeeProfileService employeeProfileService){
        return new CrudEmployeeViewModel(employeeService, employeeProfileService);
    }

    @Bean
    @Scope(value = "prototype")
    public CrudEmployeeProfileViewModel crudEmployeeProfileViewModel(EmployeeProfileService employeeProfileService){
        return new CrudEmployeeProfileViewModel(employeeProfileService);
    }


}
