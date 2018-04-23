package de.flxplzk.frontend.ui;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import de.flxplzk.frontend.backend.domain.User;
import de.flxplzk.frontend.backend.service.UserService;
import de.flxplzk.frontend.ui.common.event.TimeSaverEventBus;
import de.flxplzk.frontend.ui.common.event.TimerSaverEvent;
import de.flxplzk.frontend.ui.common.event.TimerSaverEvent.UserLoggedOutEvent;
import de.flxplzk.frontend.ui.common.event.TimerSaverEvent.UserLoginRequestedEvent;
import de.flxplzk.frontend.ui.view.components.LoginView;
import de.flxplzk.frontend.ui.view.components.MainViewComponent;
import de.flxplzk.frontend.ui.view.components.TransactionView;
import de.flxplzk.vaadin.mvvm.ViewModelComposer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author flxplzk
 */
@Theme("valo")
@Push
@SpringUI
public class TimeSaverUI extends UI {

    private final UserService userService;
    private final SpringNavigator navigator;
    private final SpringViewProvider viewProvider;
    private final TimeSaverEventBus eventBus;
    private final ViewModelComposer viewModelComposer;

    private MainViewComponent mainView;

    @Autowired
    public TimeSaverUI(TimeSaverEventBus eventBus, UserService userService, SpringNavigator navigator, SpringViewProvider viewProvider, ViewModelComposer viewModelComposer) {
        this.eventBus = eventBus;
        this.userService = userService;
        this.navigator = navigator;
        this.viewProvider = viewProvider;
        this.viewModelComposer = viewModelComposer;
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
            this.mainView = new MainViewComponent(viewModelComposer);
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
        getSpringNavigator().addViewChangeListener(viewChangeEvent -> {
            getWindows().forEach(Window::close);
            return true;
        });
        if ("".equals(this.getSpringNavigator().getState())){
            this.getSpringNavigator().navigateTo(TransactionView.VIEW_NAME);
        }

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

    public SpringNavigator getSpringNavigator() {
        return this.navigator;
    }
}