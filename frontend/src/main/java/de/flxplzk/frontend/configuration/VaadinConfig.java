package de.flxplzk.frontend.configuration;

import com.google.common.collect.Lists;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.UI;

import de.flxplzk.frontend.ui.common.event.TimeSaverEventBus;
import de.flxplzk.frontend.ui.view.components.EmployeeView;
import de.flxplzk.frontend.ui.view.components.TransactionView;
import de.flxplzk.vaadin.common.MenuItem;
import de.flxplzk.vaadin.common.WindowManager;
import de.flxplzk.vaadin.mvvm.ViewModelComposer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

@Configuration
public class VaadinConfig {

    @Bean
    @VaadinSessionScope
    public ViewModelComposer viewModelComposer(ApplicationContext applicationContext){
        return new ViewModelComposer(applicationContext);
    }

    @Bean
    @UIScope
    public WindowManager windowManager(ApplicationContext applicationContext, UI ui) {
        return new WindowManager(applicationContext, ui);
    }

    @Bean
    @VaadinSessionScope
    public Collection<MenuItem> items(){
        MenuItem create = new MenuItem("Create", VaadinIcons.PLUS_CIRCLE, MenuItem.ActionType.VOID, "", Lists.newArrayList());
        create.getChildren().add(new MenuItem("Mitarbeiterprofil anlegen", VaadinIcons.PLUS_CIRCLE, MenuItem.ActionType.WINDOW, "crudEmployeeProfileViewComponent", Lists.newArrayList()));
        create.getChildren().add(new MenuItem("Mitarbeiter anlegen", VaadinIcons.PLUS_CIRCLE, MenuItem.ActionType.WINDOW, "crudEmployeeViewComponent", Lists.newArrayList()));

        MenuItem views = new MenuItem("Data", VaadinIcons.RECORDS, MenuItem.ActionType.VOID, "", Lists.newArrayList());
        views.getChildren().add(new MenuItem("Mitarbeiter", VaadinIcons.RECORDS, MenuItem.ActionType.VIEW, EmployeeView.VIEW_NAME, Lists.newArrayList()));
        views.getChildren().add(new MenuItem("Transaktionen", VaadinIcons.RECORDS, MenuItem.ActionType.VIEW, TransactionView.VIEW_NAME, Lists.newArrayList()));

        return Lists.newArrayList(create, views);
    }

    @Bean
    @UIScope
    public TimeSaverEventBus timeSaverEventBus(){
        return new TimeSaverEventBus();
    }

}
