package university.vaadin.ui.admin.course.profile;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.ui.admin.course.list.CoursesPage;
import university.vaadin.ui.common.page.DetailsPage;
import university.vaadin.ui.layout.MainPage;

@Route(value = "admin/courses/details", layout = MainPage.class)
@PageTitle("Courses")
@RolesAllowed({"ADMIN"})
public class CoursePage extends DetailsPage<CourseResponse> {


    public CoursePage(CoursePresenter presenter){
        super(presenter,"← Back to list");
        grid.addColumn(StudentResponse::groupName).setHeader("Group Name").setSortable(true);
    }


    @Override
    public void show(CourseResponse course) {
        title.setText(course.courseName());
        info.setText("Professor: " + course.professorName());
    }

    @Override
    public void navigateBack() {
        UI.getCurrent().navigate(CoursesPage.class);
    }

}

