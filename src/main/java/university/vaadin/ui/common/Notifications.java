package university.vaadin.ui.common;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class Notifications {


    public static void success(String message) {
        Notification notification = Notification.show(message, 3000, Notification.Position.BOTTOM_END);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
    public static void error(String message) {
        Notification notification = Notification.show(message, 3000, Notification.Position.BOTTOM_END);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
