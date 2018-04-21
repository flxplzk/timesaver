package de.flxplzk.frontend.ui.components.menu;

import com.vaadin.server.Resource;

public class MenuItem {

    private final String viewName;
    private final Resource icon;

    public MenuItem(String viewName, Resource icon) {
        this.viewName = viewName;
        this.icon = icon;
    }

    public String getViewName() {
        return viewName;
    }

    public Resource getIcon() {
        return icon;
    }
}
