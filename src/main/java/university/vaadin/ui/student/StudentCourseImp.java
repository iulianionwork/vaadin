package university.vaadin.ui.student;

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

@Route(value = "my-courses", layout = MainLayout.class)
@PageTitle("Courses")
@RolesAllowed({"STUDENT"})
public class StudentCourseImp extends VerticalLayout implements StudentCourseView {

    private final Grid<CourseResponse> grid = new  Grid<>(CourseResponse.class, false);

    private final Paragraph error = new Paragraph("You are not enrolled in any course yet.");

    public StudentCourseImp(StudentCoursePresenter presenter) {

        grid.addColumn(CourseResponse::courseName).setHeader("Course").setSortable(true);
        grid.addColumn(CourseResponse::professorName).setHeader("Professor").setSortable(true);
        add(new H2("My Courses"), error, grid );
        presenter.init(this);
    }


    @Override
    public void showCourses(List<CourseResponse> courses) {
        grid.setItems(courses);
        error.setVisible(courses.isEmpty());
        grid.setVisible(!courses.isEmpty());
    }
}
