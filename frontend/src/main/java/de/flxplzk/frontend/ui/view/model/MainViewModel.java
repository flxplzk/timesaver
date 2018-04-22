package de.flxplzk.frontend.ui.view.model;

import com.google.common.collect.Lists;
import com.vaadin.data.HasValue;
import de.flxplzk.frontend.ui.TimeSaverUI;
import de.flxplzk.vaadin.common.MenuItem;
import de.flxplzk.vaadin.common.WindowManager;
import de.flxplzk.vaadin.mvvm.Property;

import java.util.Collection;
import java.util.List;

import static de.flxplzk.vaadin.common.MenuItem.ActionType.VIEW;

/**
 * ViewModel for the mainView. on selection of a menuItem a new Window
 * will be opened or, in case the ActionType is VIEW, the view will
 * be navigated.
 *
 * @author felix plazek
 */
public class MainViewModel {

    protected static final MenuItem DEFAULT_ITEM = new MenuItem(
            "DEFAULT",
            null,
            null,
            "DEFAULT",
            Lists.newArrayList()
    );
    private final Property<MenuItem> selectedItem = new Property<>(DEFAULT_ITEM);
    private final Property<List<MenuItem>> menuItems = new Property<>(Lists.newArrayList());

    public MainViewModel(WindowManager windowManager, Collection<MenuItem> items, TimeSaverUI currentUI) {
        this.menuItems.setValue(Lists.newArrayList(items));
        this.selectedItem.addValueChangeListener(new MenuItemSelectionHandler(selectedItem, windowManager, currentUI));
    }

    public static class MenuItemSelectionHandler implements HasValue.ValueChangeListener<MenuItem> {

        private final Property<MenuItem> selectedItem;
        private final transient WindowManager windowManager;
        private final transient TimeSaverUI currentUI;

        public MenuItemSelectionHandler(Property<MenuItem> selectedItem, WindowManager windowManager, TimeSaverUI currentUI) {
            this.selectedItem = selectedItem;
            this.windowManager = windowManager;
            this.currentUI = currentUI;
        }

        @Override
        public void valueChange(HasValue.ValueChangeEvent<MenuItem> event) {
            if (selectedItem.getValue().equals(DEFAULT_ITEM)) {
                return;
            }
            switch (selectedItem.getValue().getActionType()) {
                case VIEW:
                    windowManager.removeCurrentWindow();
                    currentUI.getSpringNavigator().navigateTo(selectedItem.getValue().getViewName());
                    selectedItem.setValue(DEFAULT_ITEM);
                    break;
                case WINDOW:
                    windowManager.addWindow(selectedItem.getValue().getViewName());
                    selectedItem.setValue(DEFAULT_ITEM);
                    break;
                case VOID:
                    break;
            }
        }
    }
}
