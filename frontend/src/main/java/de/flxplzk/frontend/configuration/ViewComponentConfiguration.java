package de.flxplzk.frontend.configuration;

import de.flxplzk.frontend.ui.view.components.CrudEmployeeProfileViewComponent;
import de.flxplzk.frontend.ui.view.components.CrudEmployeeViewComponent;
import de.flxplzk.frontend.ui.view.components.CrudTransactionViewComponent;
import de.flxplzk.vaadin.common.ViewComponent;
import de.flxplzk.vaadin.mvvm.ViewModelComposer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ViewComponentConfiguration {

    @Bean
    @Scope(value = "prototype")
    public ViewComponent crudEmployeeProfileViewComponent(ViewModelComposer viewModelComposer) {
        return new CrudEmployeeProfileViewComponent(viewModelComposer);
    }

    @Bean
    @Scope(value = "prototype")
    public ViewComponent crudEmployeeViewComponent(ViewModelComposer viewModelComposer) {
        return new CrudEmployeeViewComponent(viewModelComposer);
    }

    @Bean
    @Scope(value = "prototype")
    public ViewComponent crudTransactionViewComponent(ViewModelComposer viewModelComposer) {
        return new CrudTransactionViewComponent(viewModelComposer);
    }

}
