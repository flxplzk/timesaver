package de.flxplzk.frontend.ui.components;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.flxplzk.frontend.ui.TimeSaverUI;
import de.flxplzk.frontend.ui.common.event.TimeSaverEventBus;
import de.flxplzk.frontend.ui.common.event.TimerSaverEvent;

/**
 * LogInView is called if no user is saved ass VaadinSessionAtribute. If so id adds a LogIn Modal to the current UI.
 * when loginbutton is pressed Loginview posts a LoginRequestedEvent to TimeSaverEventBus.
 *
 * @author flxplzk
 */
public class LoginView extends VerticalLayout { // TODO : eigene Klasse LiginWindow and WelcomePage

    private Window window = new Window();
    private final TimeSaverUI ui;
    private final TimeSaverEventBus eventBus;

    /**
     * default constructor. init the loginmodal and adds it to current UI.
     */
    public LoginView(TimeSaverUI ui, TimeSaverEventBus eventBus) {
        this.ui = ui;
        this.eventBus = eventBus;
        setSizeFull();
        setMargin(false);
        setSpacing(false);
        window.setModal(true);
        window.setClosable(false);
        window.setResizable(false);
        window.setSizeUndefined();
        window.setContent(buildFields());

        this.ui.addWindow(window);
    }

    /**
     * builds the needed Fields for login process
     *
     * @return
     */
    private Component buildFields() {
        VerticalLayout fields = new VerticalLayout();
        fields.addStyleName("fields");

        final TextField username = new TextField("Username");
        username.setIcon(FontAwesome.USER);
        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        username.setSizeFull();

        final PasswordField password = new PasswordField("Password");
        password.setIcon(FontAwesome.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        password.setSizeFull();

        final Button signin = new Button("Sign In");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        signin.focus();
        signin.setSizeFull();

        fields.addComponents(buildLabels(), username, password, signin);
        fields.setComponentAlignment(signin, Alignment.MIDDLE_CENTER);

        signin.addClickListener(clickEvent -> {
                this.eventBus.post(new TimerSaverEvent.UserLoginRequestedEvent(username
                        .getValue(), password.getValue()));
                this.ui.removeWindow(window);
        });
        return fields;
    }

    /**
     * builds needed labels
     *
     * @return
     */
    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        return labels;
    }
}
