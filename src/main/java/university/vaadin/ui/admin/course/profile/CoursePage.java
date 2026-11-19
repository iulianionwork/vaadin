package university.vaadin.ui.admin.course.profile;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.ui.admin.course.list.CoursesPage;
import university.vaadin.ui.common.Notifications;
import university.vaadin.ui.layout.MainPage;
import university.vaadin.ui.common.DetailsView;

import java.util.List;

@Route(value = "admin/courses/details", layout = MainPage.class)
@PageTitle("Courses")
@RolesAllowed({"ADMIN"})
public class CoursePage extends VerticalLayout implements DetailsView<CourseResponse>, HasUrlParameter<Long> {

    private final CoursePresenter presenter;
    private final H1 title = new H1();
    private final Span professorName = new Span();
    private final Grid<StudentResponse> grid =  new Grid<>(StudentResponse.class, false);

    public CoursePage(CoursePresenter presenter){
        this.presenter = presenter;

        Button back = new Button("← Back to list", e -> presenter.onBackClicked());
        back.addClickShortcut(Key.ESCAPE);
        back.addThemeVariants(ButtonVariant.LUMO_WARNING);

        grid.addColumn(StudentResponse::firstName).setHeader("First Name").setSortable(true);
        grid.addColumn(StudentResponse::lastName).setHeader("Last Name").setSortable(true);
        grid.addColumn(StudentResponse::email).setHeader("Email");
        grid.addColumn(StudentResponse::groupName).setHeader("Group Name").setSortable(true);

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();

        header.add(title);
        header.addAndExpand(new Div()); // spacer
        header.add(back);


        add(header,new Div(), new H3("Professor:"), professorName, new Div() , new H3("Students:"), grid);

        presenter.init(this);

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        presenter.load(aLong);
    }

    @Override
    public void show(CourseResponse course) {
        title.setText(course.courseName());
        professorName.setText(course.professorName());
    }

    @Override
    public void navigateBack() {
        UI.getCurrent().navigate(CoursesPage.class);
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

