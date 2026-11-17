package university.vaadin.ui.admin.professor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.professor.CreateProfessorRequest;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.ui.admin.student.StudentDetailsImp;
import university.vaadin.ui.common.AbstractListView;
import university.vaadin.ui.layout.MainLayout;

@Route(value = "/admin/professors", layout = MainLayout.class)
@PageTitle("Students")
@RolesAllowed("ADMIN")
    public class ProfessorList extends AbstractListView<ProfessorResponse, CreateProfessorRequest> {


    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final PasswordField password = new PasswordField("Password");
    private final TextField department = new TextField("Department");

    public ProfessorList(ProfessorListPresenter presenter) {
        super("Professors",
                "Professor",
                ProfessorResponse.class,
                presenter,
                ProfessorResponse::id,
                id -> UI.getCurrent().navigate(StudentDetailsImp.class, id));
        registerFiled("First Name", firstName);
        registerFiled("Last Name", lastName);
        registerFiled("Department", department);
        registerFiled("Password", password);
        build();

        presenter.init(this);
    }


    @Override
    protected void configureColumns(Grid<ProfessorResponse> grid) {
        grid.addColumn(ProfessorResponse::id).setHeader("ID").setSortable(true);
        grid.addColumn(ProfessorResponse::email).setHeader("Email").setSortable(true);
        grid.addColumn(ProfessorResponse::firstName).setHeader("First Name").setSortable(true);
        grid.addColumn(ProfessorResponse::lastName).setHeader("Last Name").setSortable(true);
        grid.addColumn(ProfessorResponse::department).setHeader("Department").setSortable(true);
    }

    @Override
    protected Component createForm() {
        return new FormLayout(firstName, lastName, department, password);
    }

    @Override
    protected CreateProfessorRequest buildRequest() {
        return new CreateProfessorRequest(firstName.getValue(), lastName.getValue(), password.getValue(), department.getValue());
    }
}
