package university.vaadin.ui.admin.course;


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
import university.vaadin.ui.admin.student.StudentDetailsImp;
import university.vaadin.ui.common.AbstractListView;
import university.vaadin.ui.layout.MainLayout;

import java.util.List;

@Route(value = "admin/courses", layout = MainLayout.class)
@PageTitle("Courses")
@RolesAllowed("ADMIN")
public class CourseList extends AbstractListView<CourseResponse, CourseRequest> implements CoursePresenter {

    private final TextField courseName = new TextField("Course Name");
    private final ComboBox<ProfessorResponse> professor = new ComboBox<>("Professor");

    public CourseList(CourseListPresenter presenter) {
        super("Courses",
                "Course",
                CourseResponse.class,
                presenter,
                CourseResponse::id,
                id -> UI.getCurrent().navigate(StudentDetailsImp.class, id));
        professor.setItemLabelGenerator(p -> p.firstName() + " " + p.lastName());

        registerFiled("Course Name", courseName);
        registerFiled("Professor Name", professor);
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
