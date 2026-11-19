package university.vaadin.ui.layout;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import university.vaadin.backend.service.SecurityService;
import university.vaadin.ui.admin.student.list.StudentsPage;
import university.vaadin.ui.profile.ProfilePage;

@Route(value = "", layout = MainPage.class)
@PageTitle("University")
@PermitAll
public class HomePage extends Div implements BeforeEnterObserver {


    private final SecurityService securityService;

    public HomePage(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {


        if (securityService.hasRole("ADMIN")) {

            event.forwardTo(StudentsPage.class);

        } else{
            event.forwardTo(ProfilePage.class);
        }
    }
}
