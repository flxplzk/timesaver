package de.flxplzk.frontend.ui.view.components;

import com.google.common.collect.Lists;

import com.vaadin.data.HasItems;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.vaadin.common.AbstractViewComponent;
import de.flxplzk.vaadin.common.HasValueComponent;
import de.flxplzk.vaadin.common.MenuItem;
import de.flxplzk.vaadin.mvvm.ItemBound;
import de.flxplzk.vaadin.mvvm.ListingBound;
import de.flxplzk.vaadin.mvvm.View;
import de.flxplzk.vaadin.mvvm.ViewModelComposer;

import java.util.Collection;
import java.util.List;


/**
 * MainViewComponent is the center of the application. all usable views will be view in the contentContainer
 * of this View.
 *
 * @author felix plazek
 */
@View(model = "mainViewModel")
public class MainViewComponent extends AbstractViewComponent {

    // ################################# UI COMPONENTS #################################

    @ItemBound(to = "selectedItem")
    @ListingBound(to = "menuItems")
    private final MenuComponent menuComponent = new MenuComponent();
    private final ComponentContainer contentContainer = new CssLayout();

    // ################################# UI COMPONENTS #################################

    private final VerticalLayout rootLayout = new VerticalLayout(
            this.menuComponent,
            this.contentContainer
    );

    public MainViewComponent(ViewModelComposer viewModelComposer) {
        super(viewModelComposer);
        this.bind();
        this.init();
    }

    private void init() {
        this.setSizeFull();
        this.setCompositionRoot(this.rootLayout);

        this.rootLayout.setMargin(false);
        this.rootLayout.setSpacing(false);
        this.rootLayout.setSizeFull();
        this.rootLayout.setExpandRatio(contentContainer, 1.0f);

        this.contentContainer.addStyleName("view-content");
        this.contentContainer.setSizeFull();
    }

    public ComponentContainer getContentContainer() {
        return this.contentContainer;
    }

    /**
     * This View provides all navigation options.
     *
     * @author felix plazek
     */
    public static class MenuComponent extends HasValueComponent<MenuItem> implements HasItems<MenuItem> {

        private transient List<MenuItem> menuItems = Lists.newArrayList();

        // ################################# UI COMPONENTS #################################

        private final MenuBar menuBar = new MenuBar();
        private final Label logo = new Label("<strong>TIMESAVER</strong> Burse", ContentMode.HTML);
        private final HorizontalLayout logoWrapper = new HorizontalLayout(logo);

        // ################################# UI STRUCTURE #################################

        private final HorizontalLayout rootLayout = new HorizontalLayout(
                this.logoWrapper,
                this.menuBar
        );

        public MenuComponent() {
            super(new MenuItem("DEFAULT", null, null, "DEFAULT", Lists.newArrayList()));
            build();
        }

        private void build() {
            this.setCompositionRoot(this.rootLayout);
            this.menuBar.setStyleName(ValoTheme.MENUBAR_BORDERLESS);
            this.rootLayout.setMargin(false);
            this.rootLayout.setComponentAlignment(menuBar, Alignment.MIDDLE_LEFT);
            this.buildTitle();
            this.builtMenuItems();
        }

        private void builtMenuItems() {
            this.menuBar.removeItems();
            for (MenuItem menuItem : this.menuItems) {
                MenuBar.MenuItem item = this.menuBar.addItem(
                        menuItem.getCaption(),
                        menuItem.getIcon(),
                        null
                );
                appendChildren(item, menuItem.getChildren());
            }
        }

        private void buildTitle() {
            logo.setSizeUndefined();
            logoWrapper.setWidth(200, Unit.PIXELS);
            logoWrapper.setHeight(50, Unit.PIXELS);
            logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
            logoWrapper.addStyleName(ValoTheme.MENU_TITLE);
            logoWrapper.setSpacing(false);
        }

        @Override
        public DataProvider<MenuItem, ?> getDataProvider() {
            return null;
        }

        @Override
        public void setItems(Collection<MenuItem> items) {
            this.menuItems = Lists.newArrayList(items);
            this.builtMenuItems();
        }

        @Override
        protected void renderContent() {
            // nothing to render if a new item is selected.
        }

        private void appendChildren(MenuBar.MenuItem item, List<MenuItem> children) {
            for (MenuItem child : children) {
                MenuBar.MenuItem childItem = item.addItem(
                        child.getCaption(),
                        child.getIcon(),
                        (MenuBar.Command) selectedItem -> valueProperty.setValue(child)
                );
                appendChildren(childItem, child.getChildren());
            }
        }
    }
}
