package university.vaadin.ui.admin.student;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.student.CreateStudentRequest;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.ui.common.AbstractListView;
import university.vaadin.ui.layout.MainLayout;

@Route(value = "admin/students", layout = MainLayout.class)
@PageTitle("Students")
@RolesAllowed("ADMIN")
public class StudentList extends AbstractListView<StudentResponse, CreateStudentRequest> {

    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final PasswordField password = new PasswordField("Password");


    public StudentList(StudentListPresenter presenter) {
        super("Students",
                "Student",
                StudentResponse.class,
                presenter,
                StudentResponse::id,
                id -> UI.getCurrent().navigate(StudentDetailsImp.class, id));
        registerFiled("First Name", firstName);
        registerFiled("Last Name", lastName);
        registerFiled("Password", password);
        build();

        presenter.init(this);
    }


    @Override
    protected void configureColumns(Grid<StudentResponse> grid) {
        grid.addColumn(StudentResponse::id).setHeader("ID").setSortable(true);
        grid.addColumn(StudentResponse::email).setHeader("Email").setSortable(true);
        grid.addColumn(StudentResponse::firstName).setHeader("First Name").setSortable(true);
        grid.addColumn(StudentResponse::lastName).setHeader("Last Name").setSortable(true);
        grid.addColumn(StudentResponse::groupName).setHeader("Group Name").setSortable(true);
    }

    @Override
    protected Component createForm() {
        return new FormLayout(firstName,lastName,password);
    }

    @Override
    protected CreateStudentRequest buildRequest() {
        return new CreateStudentRequest(firstName.getValue(), lastName.getValue(), password.getValue());
    }

}
