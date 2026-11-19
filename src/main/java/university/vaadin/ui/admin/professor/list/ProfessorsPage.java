package university.vaadin.ui.admin.professor.list;

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
import university.vaadin.ui.admin.professor.profile.ProfessorPage;
import university.vaadin.ui.common.ListPage;
import university.vaadin.ui.layout.MainPage;

@Route(value = "/admin/professors", layout = MainPage.class)
@PageTitle("Students")
@RolesAllowed("ADMIN")
    public class ProfessorsPage extends ListPage<ProfessorResponse, CreateProfessorRequest> {


    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final PasswordField password = new PasswordField("Password");
    private final TextField department = new TextField("Department");

    public ProfessorsPage(ProfessorsPresenter presenter) {
        super("Professors",
                "Professor",
                ProfessorResponse.class,
                presenter,
                ProfessorResponse::id,
                id -> UI.getCurrent().navigate(ProfessorPage.class, id));
        registerField("First Name", firstName);
        registerField("Last Name", lastName);
        registerField("Department", department);
        registerField("Password", password);
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
