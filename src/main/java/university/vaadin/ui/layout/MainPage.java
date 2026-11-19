package university.vaadin.ui.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import jakarta.annotation.security.PermitAll;
import university.vaadin.ui.admin.course.list.CoursesPage;
import university.vaadin.ui.admin.group.list.GroupsPage;
import university.vaadin.ui.admin.professor.list.ProfessorsPage;
import university.vaadin.ui.admin.student.list.StudentsPage;
import university.vaadin.ui.profile.ProfilePage;

@PermitAll
public class MainPage extends AppLayout implements MainView {

    private final SideNav menu = new SideNav();
    private final Span userName = new Span();

    public MainPage(MainPresenter presenter) {
        H1 title = new H1("University");

        Button logoutButton = new Button("Logout", event -> {presenter.onLogoutClick();});
        logoutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        logoutButton.addThemeVariants(ButtonVariant.LARGE);
        HorizontalLayout right = new HorizontalLayout();
        right.setWidthFull();
        right.add(title);
        right.addAndExpand(new Div());
        right.add(logoutButton);



        addToNavbar(new DrawerToggle(), right);
        addToDrawer(menu);

        presenter.init(this);
    }

    @Override
    public void showAdminMenu() {
        menu.addItem(new SideNavItem("Students", StudentsPage.class));
        menu.addItem(new SideNavItem("Professors", ProfessorsPage.class));
        menu.addItem(new SideNavItem("Courses", CoursesPage.class));
        menu.addItem(new SideNavItem("Groups", GroupsPage.class));

    }

    @Override
    public void showStudentMenu() {
        menu.addItem(new SideNavItem("Profile", ProfilePage.class));
    }

    @Override
    public void showProfessorMenu() {
        menu.addItem(new SideNavItem("Profile", ProfilePage.class));

    }

    @Override
    public void showUserName(String name) {
        userName.setText(name);
    }
}
