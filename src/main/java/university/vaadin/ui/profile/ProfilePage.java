package university.vaadin.ui.profile;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.ui.layout.MainPage;
import university.vaadin.ui.professor.ProfessorCoursePage;

import java.util.List;
import java.util.Map;

@Route(value = "profile", layout = MainPage.class)
@PageTitle("Profile")
@RolesAllowed({"STUDENT", "PROFESSOR"})
public class ProfilePage extends VerticalLayout  implements ProfileView {

    private final  ProfilePresenter presenter;
    private final H1 welcome = new H1();
    private final FormLayout form = new FormLayout();
    private final Grid<CourseResponse> courseGrid = new Grid<>(CourseResponse.class, false);

    public ProfilePage(ProfilePresenter presenter) {
        this.presenter = presenter;

        form.setMaxWidth("600px");
        courseGrid.addColumn(CourseResponse::courseName).setHeader("Course");
        courseGrid.setWidthFull();
        VerticalLayout profile =  new VerticalLayout( new H2("Profile:"), form);
        VerticalLayout course =  new VerticalLayout( new H2("Courses:"), courseGrid);
        HorizontalLayout content = new HorizontalLayout(profile,course);
        content.setWidthFull();


        add(welcome, content);
        presenter.init(this);
    }

    @Override
    public void showWelcome(String firstName) {
        welcome.setText("Welcome back, " + firstName + " !");
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
    public void showCourses(List<CourseResponse> courses, boolean withDetails) {
        if(withDetails){
            courseGrid.addComponentColumn(s -> new Button("Details", e -> presenter.onCourseClicked(s.id())));
        } else{
            courseGrid.addColumn(CourseResponse::professorName).setHeader("Professor");
        }
        courseGrid.setItems(courses);
    }

    @Override
    public void navigateToCourse(Long id) {
        UI.getCurrent().navigate(ProfessorCoursePage.class, id);
    }

}
