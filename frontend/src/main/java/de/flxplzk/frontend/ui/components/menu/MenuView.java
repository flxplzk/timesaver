package de.flxplzk.frontend.ui.components.menu;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.frontend.ui.common.event.TimeSaverEventBus;
import de.flxplzk.frontend.ui.common.event.TimerSaverEvent;

/**
 * MenuView displays navigation and action options in a fixed frame on the left.
 *
 * @author flxplzk
 */
public class MenuView extends CustomComponent {

    private final TimeSaverEventBus eventBus;
    private final SpringViewProvider viewProvider;

    /**
     * default constructor building UI.
     * @param viewProvider
     */
    public MenuView(TimeSaverEventBus eventBus, SpringViewProvider viewProvider) {
        this.eventBus = eventBus;
        this.viewProvider = viewProvider;
        setPrimaryStyleName("valo-menu");
        setSizeUndefined();
        setCompositionRoot(buildContent());
    }

    /**
     * method builds and returns the content component.
     * @return
     */
    private Component buildContent() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildMenuItems());
        return menuContent;
    }

    /**
     * method returns html content for the application title.
     *
     * @return
     */
    private Component buildTitle() {
        Label logo = new Label("Timesaver <strong>Burse</strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        logoWrapper.setSpacing(false);
        return logoWrapper;
    }

    /**
     * adding menu buttons to menu component.
     *
     * @return
     */
    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");

        for (final String viewName : viewProvider.getViewNamesForCurrentUI()) {
            Component menuItemComponent = new TimeSaverMenuItemButton(((MenuItemProvider) viewProvider.getView(viewName)).getMenuItem());
            menuItemsLayout.addComponent(menuItemComponent);
        }
        return menuItemsLayout;
    }

    /**
     * Custom menu button.
     */
    private final class TimeSaverMenuItemButton extends Button {

        public TimeSaverMenuItemButton(final MenuItem menuItem) {
            setPrimaryStyleName("valo-menu-item");
                setIcon(menuItem.getIcon());
                setCaption(menuItem.getViewName().toUpperCase());
            addClickListener((ClickListener) event -> eventBus.post(new TimerSaverEvent.NavigationRequestEvent(menuItem.getViewName())));
        }
    }
}
