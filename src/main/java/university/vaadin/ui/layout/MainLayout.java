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
import university.vaadin.ui.admin.course.CourseList;
import university.vaadin.ui.admin.group.GroupList;
import university.vaadin.ui.admin.professor.ProfessorList;
import university.vaadin.ui.admin.student.StudentList;
import university.vaadin.ui.professor.ProfessorCoursesImp;
import university.vaadin.ui.profile.ProfileProfessorViewImp;
import university.vaadin.ui.profile.ProfileViewImp;
import university.vaadin.ui.student.StudentCourseImp;

@PermitAll
public class MainLayout extends AppLayout implements MainLayoutView {

    private final SideNav menu = new SideNav();
    private final Span userName = new Span();

    public MainLayout(MainLayoutPresenter presenter) {
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
        menu.addItem(new SideNavItem("Students", StudentList.class));
        menu.addItem(new SideNavItem("Professors", ProfessorList.class));
        menu.addItem(new SideNavItem("Courses", CourseList.class));
        menu.addItem(new SideNavItem("Groups", GroupList.class));

    }

    @Override
    public void showStudentMenu() {
        menu.addItem(new SideNavItem("Profile", ProfileViewImp.class));
        menu.addItem(new SideNavItem("Password", StudentCourseImp.class));
    }

    @Override
    public void showProfessorMenu() {
        menu.addItem(new SideNavItem("Profile", ProfileProfessorViewImp.class));
        menu.addItem(new SideNavItem("Password", ProfessorCoursesImp.class));

    }

    @Override
    public void showUserName(String name) {
        userName.setText(name);
    }
}
