package university.vaadin.ui.professor;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.ui.common.DetailsView;
import university.vaadin.ui.common.Notifications;
import university.vaadin.ui.layout.MainPage;
import university.vaadin.ui.profile.ProfilePage;

import java.util.List;

@Route(value = "teaching/course", layout = MainPage.class)
@PageTitle("Courses")
@RolesAllowed({"PROFESSOR"})
public class ProfessorCoursePage extends VerticalLayout  implements DetailsView<CourseResponse>, HasUrlParameter<Long> {

        private final ProfessorCoursePresenter presenter;
        private final H2 title = new H2();
        private final Grid<StudentResponse> grid =  new Grid<>(StudentResponse.class, false);

        public ProfessorCoursePage(ProfessorCoursePresenter presenter){
            this.presenter = presenter;

            Button back = new Button("← Back to profile", e -> presenter.onBackClicked());
            back.addClickShortcut(Key.ESCAPE);

            grid.addColumn(StudentResponse::firstName).setHeader("First Name").setSortable(true);
            grid.addColumn(StudentResponse::lastName).setHeader("Last Name").setSortable(true);
            grid.addColumn(StudentResponse::email).setHeader("Email");
            grid.addColumn(StudentResponse::groupName).setHeader("Group Name").setSortable(true);

            HorizontalLayout header = new HorizontalLayout();
            header.setWidthFull();

            header.add(title);
            header.addAndExpand(new Div()); // spacer
            header.add(back);


            add(header, grid);

            presenter.init(this);

        }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        presenter.load(aLong);
    }

    @Override
    public void show(CourseResponse course) {
            title.setText(course.courseName());

    }

    @Override
    public void navigateBack() {
        UI.getCurrent().navigate(ProfilePage.class);
    }

    @Override
    public void showStudents(List<StudentResponse> students) {
            grid.setItems(students);
    }

    @Override
    public void showError(String message) {
        Notifications.error(message);
    }
}
