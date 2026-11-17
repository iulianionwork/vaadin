package university.vaadin.ui.profile;

import java.util.Map;

public interface ProfileView {
    void showWelcome(String firstName);
    void showDetails(Map<String,String> details);
    void showSuccess(String message);
    void showError(String message);
}
