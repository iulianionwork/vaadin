package university.vaadin.ui.professor;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.ui.layout.MainLayout;

import java.util.List;

@Route(value = "teaching", layout = MainLayout.class)
@PageTitle("Courses")
@RolesAllowed({"PROFESSOR"})
public class ProfessorCoursesImp extends VerticalLayout implements ProfessorCourseView {

    private final Grid<CourseResponse> grid = new  Grid<>(CourseResponse.class, false);

    private final Paragraph error = new Paragraph("You are not enrolled in any course yet.");

    public ProfessorCoursesImp(ProfessorCoursePresenter presenter) {

        grid.addColumn(CourseResponse::courseName).setHeader("Course").setSortable(true);
        grid.addComponentColumn(s -> new Button("Details", e -> presenter.onCourseClick(s.id())));
        add(new H2("My Courses"), error, grid );
        presenter.init(this);
    }

    @Override
    public void showCourses(List<CourseResponse> courses) {
        grid.setItems(courses);
        error.setVisible(courses.isEmpty());
        grid.setVisible(!courses.isEmpty());
    }

    @Override
    public void navigateToCourse(Long id) {
        UI.getCurrent().navigate(ProfessorCourseDetailsViewImp.class, id);
    }
}
