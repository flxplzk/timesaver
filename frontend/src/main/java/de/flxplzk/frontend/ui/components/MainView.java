package de.flxplzk.frontend.ui.components;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import de.flxplzk.frontend.ui.components.menu.MenuView;

/**
 * MainView acts as a content container.
 *
 * @author flxplzk
 */
public class MainView extends HorizontalLayout{

    private final ComponentContainer contentContainer;
    private final MenuView menuComponent;

    public MainView(MenuView menuComponent) {
        this.menuComponent = menuComponent;
        setSizeFull();
        addStyleName("mainview");
        setSpacing(false);
        this.contentContainer = buildContentComponent();
        addComponent(this.menuComponent);
        addComponent(contentContainer);
        setExpandRatio(contentContainer, 1.0f);
    }

    private ComponentContainer buildContentComponent() {
        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        return content;
    }


    public ComponentContainer getContentContainer() {
        return contentContainer;
    }

}
