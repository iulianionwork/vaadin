package university.vaadin.ui.profile;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.ui.common.Notifications;
import university.vaadin.ui.layout.MainLayout;

import java.util.Map;

@Route(value = "profile", layout = MainLayout.class)
@PageTitle("Profile")
@RolesAllowed({"STUDENT", "PROFESSOR"})
public class ProfileViewImp extends VerticalLayout  implements ProfileView {

    private final H2 welcome = new H2();
    private final FormLayout form = new FormLayout();

    public ProfileViewImp(ProfilePresenter presenter) {
        form.setMaxWidth("600px");
        add(welcome, form);
        presenter.init(this);
    }

    @Override
    public void showWelcome(String firstName) {
        welcome.setText("Welcome " + firstName + " !");
    }

    @Override
    public void showDetails(Map<String, String> details) {
        details.forEach((label, value) -> {
                TextField field = new TextField(label);
        field.setValue(value);
        field.setReadOnly(true);

        form.add(field);});
    }

    @Override
    public void showSuccess(String message) {
        Notifications.success(message);
    }

    @Override
    public void showError(String message) {
        Notifications.error(message);
    }
}
