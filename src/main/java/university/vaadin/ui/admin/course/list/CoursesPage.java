package university.vaadin.ui.admin.course.list;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.course.CourseRequest;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.ui.admin.course.profile.CoursePage;
import university.vaadin.ui.common.page.ListPage;
import university.vaadin.ui.layout.MainPage;

import java.util.List;

@Route(value = "admin/courses", layout = MainPage.class)
@PageTitle("Courses")
@RolesAllowed("ADMIN")
public class CoursesPage extends ListPage<CourseResponse, CourseRequest> implements CourseView {

    private final TextField courseName = new TextField("Course Name");
    private final ComboBox<ProfessorResponse> professor = new ComboBox<>("Professor");

    public CoursesPage(CoursesPresenter presenter) {
        super("Courses",
                "Course",
                CourseResponse.class,
                presenter,
                CourseResponse::id,
                id -> UI.getCurrent().navigate(CoursePage.class, id));
        professor.setItemLabelGenerator(p -> p.firstName() + " " + p.lastName());

        registerField("courseName", courseName);
        registerField("professorId", professor);
        build();

        presenter.init(this);
    }



    @Override
    protected void configureColumns(Grid<CourseResponse> grid) {
        grid.addColumn(CourseResponse::id).setHeader("ID").setSortable(true);
        grid.addColumn(CourseResponse::courseName).setHeader("Course Name").setSortable(true);
        grid.addColumn(CourseResponse::professorName).setHeader("Professor Name").setSortable(true);
    }

    @Override
    protected Component createForm() {
        return new FormLayout(courseName, professor);
    }

    @Override
    protected CourseRequest buildRequest() {
        Long professorId = professor.getValue() == null ? null : professor.getValue().id();

        return new CourseRequest(courseName.getValue(), professorId);
    }

    @Override
    public void showProfessors(List<ProfessorResponse> professors){
        professor.setItems(professors);
    }
}
