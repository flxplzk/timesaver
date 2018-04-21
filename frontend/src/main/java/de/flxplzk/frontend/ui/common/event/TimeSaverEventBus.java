package de.flxplzk.frontend.ui.common.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import de.flxplzk.frontend.ui.TimeSaverUI;

public class TimeSaverEventBus implements SubscriberExceptionHandler {

    private final EventBus eventBus = new EventBus(this);

    public void post(final Object event) {
        this.eventBus.post(event);
    }

    public void register(final Object object) {
        this.eventBus.register(object);
    }

    public void unregister(final Object object) {
        this.eventBus.unregister(object);
    }

    @Override
    public final void handleException(final Throwable exception,
                                      final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }
}

