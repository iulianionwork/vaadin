package university.vaadin.ui.layout;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import university.vaadin.ui.admin.student.StudentList;
import university.vaadin.ui.profile.ProfileProfessorViewImp;
import university.vaadin.ui.profile.ProfileViewImp;

@Route(value = "", layout = MainLayout.class)
@PageTitle("University")
@PermitAll
public class HomeView extends Div implements BeforeEnterObserver {


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            event.forwardTo(StudentList.class);

        } else if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PROFESSOR"))) {

            event.forwardTo(ProfileProfessorViewImp.class);
        } else{
            event.forwardTo(ProfileViewImp.class);
        }
    }
}
