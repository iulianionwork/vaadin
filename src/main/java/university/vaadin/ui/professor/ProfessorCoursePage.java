package university.vaadin.ui.professor;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.ui.common.page.DetailsPage;
import university.vaadin.ui.layout.MainPage;
import university.vaadin.ui.profile.ProfilePage;

@Route(value = "teaching/course", layout = MainPage.class)
@PageTitle("Courses")
@RolesAllowed({"PROFESSOR"})
public class ProfessorCoursePage extends DetailsPage<CourseResponse> {

    public ProfessorCoursePage(ProfessorCoursePresenter presenter){
        super(presenter, "← Back to profile");
        grid.addColumn(StudentResponse::groupName).setHeader("Group Name").setSortable(true);

    }

    @Override
    public void show(CourseResponse course) {
            title.setText(course.courseName());
    }

    @Override
    public void navigateBack() {
        UI.getCurrent().navigate(ProfilePage.class);
    }

}
