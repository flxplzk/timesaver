package de.flxplzk.frontend.ui.components.welcome;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.frontend.ui.components.menu.MenuItem;
import de.flxplzk.frontend.ui.components.menu.MenuItemProvider;

/**
 * HomeView will be show options to user.
 */
@SpringView(name = HomeView.VIEW_NAME)
public class HomeView extends VerticalLayout implements MenuItemProvider {

    static final String VIEW_NAME = "home";

    public HomeView() {
        init();
    }

    /**
     * UI initialization has to be done here.
     */
    public void init() {
        Label greeting = new Label("home");
        greeting.setStyleName(ValoTheme.LABEL_H1);
        this.addComponent(greeting);
    }

    @Override
    public MenuItem getMenuItem() {
        return new MenuItem("home", FontAwesome.HOME);
    }
}
