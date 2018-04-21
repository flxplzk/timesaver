package de.flxplzk.frontend.ui;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import de.flxplzk.frontend.backend.domain.User;
import de.flxplzk.frontend.backend.service.UserService;
import de.flxplzk.frontend.ui.common.event.TimeSaverEventBus;
import de.flxplzk.frontend.ui.common.event.TimerSaverEvent;
import de.flxplzk.frontend.ui.common.event.TimerSaverEvent.UserLoggedOutEvent;
import de.flxplzk.frontend.ui.common.event.TimerSaverEvent.UserLoginRequestedEvent;
import de.flxplzk.frontend.ui.components.LoginView;
import de.flxplzk.frontend.ui.components.MainView;
import de.flxplzk.frontend.ui.components.menu.MenuView;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author flxplzk
 */
@Theme("valo")
@SpringUI
public class TimeSaverUI extends UI {

    private final UserService userService;
    private final SpringNavigator navigator;
    private final SpringViewProvider viewProvider;
    private final TimeSaverEventBus eventBus;

    private MainView mainView;

    @Autowired
    public TimeSaverUI(TimeSaverEventBus eventBus, UserService userService, SpringNavigator navigator, SpringViewProvider viewProvider) {
        this.eventBus = eventBus;
        this.userService = userService;
        this.navigator = navigator;
        this.viewProvider = viewProvider;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        this.eventBus.register(this);
        this.setNavigator(this.navigator);
        updateContent();
    }


    private void updateContent() {
        VaadinSession vaadinSession = getSession();
        User user = (User) vaadinSession
                .getAttribute(User.class.getName());
        if (user != null) {
            // Authenticated user
            this.mainView = new MainView(new MenuView(this.eventBus, this.viewProvider));
            initNavigator();
            setContent(mainView);
            removeStyleName("loginview");
        } else {
            setContent(new LoginView(this, this.eventBus));
            addStyleName("loginview");
        }
    }

    private void initNavigator() {
        ((SpringNavigator)getNavigator()).init(this, this.mainView.getContentContainer());
        getNavigator().addProvider(viewProvider);
    }

    @Subscribe
    public void userLoginRequested(final UserLoginRequestedEvent event) {
        User user = userService.authenticate(event.getUserName(),
                event.getPassword());
        VaadinSession vaadinSession = getSession();
        vaadinSession.setAttribute(User.class.getName(), user);
        updateContent();
    }


    @Subscribe
    public void userLoggedOut(final UserLoggedOutEvent event) {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't
        // invalidate the current HttpSession.
        VaadinSession vaadinSession = getSession();
        vaadinSession.close();
        getPage().reload();
    }

    @Subscribe
    public void navigationRequested(TimerSaverEvent.NavigationRequestEvent navigationRequestEvent){
        getNavigator().navigateTo(navigationRequestEvent.getDestionationViewName());
    }

}

