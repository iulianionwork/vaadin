package university.vaadin.ui.admin.professor;

import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.ui.common.ListView;
import university.vaadin.ui.common.Notifications;
import university.vaadin.ui.layout.MainLayout;

import java.util.List;

@Route(value = "/admin/professors", layout = MainLayout.class)
@PageTitle("Students")
@RolesAllowed("ADMIN")
    public class ProfessorList extends VerticalLayout implements ListView<ProfessorResponse> {

    private final ProfessorListPresenter presenter;
    private final Grid<ProfessorResponse> grid = new Grid<>(ProfessorResponse.class, false);
    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final PasswordField password = new PasswordField("Password");
    private final TextField department = new TextField("Department");

    private Dialog createDialog;
    private ConfirmDialog deleteDialog;

    public ProfessorList(ProfessorListPresenter presenter) {
        this.presenter = presenter;
        setSizeFull();

        buildGrid();
        Button addButton = new Button("Add Professor", event -> openCreateDialog());
        addButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        H2 title = new H2("Professors");
        HorizontalLayout addBar = new HorizontalLayout();
        addBar.setWidthFull();

        addBar.add(title);
        addBar.addAndExpand(new Div()); // spacer
        addBar.add(addButton);
        add(addBar, grid);

        presenter.init(this);

    }

    private void openCreateDialog() {
        firstName.clear();
        lastName.clear();
        password.clear();
        department.clear();
        clearFieldError();

        createDialog = new Dialog();
        createDialog.setHeaderTitle("Create Professor");
        createDialog.add(new VerticalLayout(firstName, lastName, department, password));
        Button save =  new Button("Save", event -> presenter.onCreateClicked(
                firstName.getValue(),
                lastName.getValue(),
                password.getValue(),
                department.getValue()
        ));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancel = new Button("Cancel", event -> createDialog.close());

        createDialog.getFooter().add(cancel, save);
        createDialog.open();
    }

    private void buildGrid() {
        grid.addColumn(ProfessorResponse::id).setHeader("ID").setSortable(true);
        grid.addColumn(ProfessorResponse::email).setHeader("Email").setSortable(true);
        grid.addColumn(ProfessorResponse::firstName).setHeader("First Name").setSortable(true);
        grid.addColumn(ProfessorResponse::lastName).setHeader("Last Name").setSortable(true);
        grid.addColumn(ProfessorResponse::department).setHeader("Department").setSortable(true);
        grid.addComponentColumn(s -> new Button("Details", e -> presenter.onDetailsClicked(s.id())));
        grid.addComponentColumn(s -> {
            Button delete = new Button("Delete", e-> confirmDialog(s.id()));
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            return delete;
        });

        grid.setSizeFull();
    }

    private void confirmDialog(Long id) {
        deleteDialog = new ConfirmDialog();
        deleteDialog.setHeader("Delete Professor");

        deleteDialog.setText("Are you sure you want to delete this professor?");
        deleteDialog.setCancelable(true);
        deleteDialog.setCancelButton("Close", event -> deleteDialog.close());
        Button confirm = new Button("Delete", event -> presenter.onDeleteClicked(id));
        confirm.addThemeVariants(ButtonVariant.ERROR);
        deleteDialog.setConfirmButton(confirm);

        deleteDialog.open();
    }



    @Override
    public void show(List<ProfessorResponse> professors) {
        grid.setItems(professors);
    }


    @Override
    public void closeCreatedDialog() {
        if(createDialog != null ){
            createDialog.close();
        }
    }

    @Override
    public void navigateToDetails(Long studentId) {
        
    }

    @Override
    public void showFieldError(String field, String message) {
        switch (field) {
            case "firstName" -> markInvalid(firstName, message);
            case "lastName" -> markInvalid(lastName, message);
            case "password" -> markInvalid(password, message);
            case "department" -> markInvalid(department, message);
        }
    }

    @Override
    public void clearFieldError() {
        firstName.setInvalid(false);
        lastName.setInvalid(false);
        password.setInvalid(false);
        department.setInvalid(false);
    }

    @Override
    public void showSuccess(String message) {
        Notifications.success(message);
    }

    @Override
    public void showError(String message) {
        Notifications.error(message);
    }

    private void markInvalid(HasValidation field, String message){
        field.setErrorMessage(message);
        field.setInvalid(true);
    }
}
