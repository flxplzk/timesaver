package de.flxplzk.frontend.ui.common.event;

public class TimerSaverEvent {

    public static final class UserLoginRequestedEvent {
        private final String userName, password;

        public UserLoginRequestedEvent(final String userName,
                                       final String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class BrowserResizeEvent {

    }

    public static class UserLoggedOutEvent {

    }

    public static class NavigationRequestEvent{

        private final String destionationViewName;

        public NavigationRequestEvent(String destionationViewName) {
            this.destionationViewName = destionationViewName;
        }

        public String getDestionationViewName() {
            return destionationViewName;
        }
    }

    public static class ChangeOnEmployeeDataEvent {

    }
}
