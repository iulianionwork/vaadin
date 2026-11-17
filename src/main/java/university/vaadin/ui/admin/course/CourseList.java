package university.vaadin.ui.admin.course;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.ui.common.ListView;
import university.vaadin.ui.layout.MainLayout;

import java.util.List;

@Route(value = "admin/courses", layout = MainLayout.class)
@PageTitle("Courses")
@RolesAllowed("ADMIN")
public class CourseList extends VerticalLayout implements ListView<CourseResponse> {

    private final CourseListPresenter presenter;
    private final Grid<CourseResponse> grid = new Grid<>(CourseResponse.class, false);
    private final TextField courseName = new TextField("Course Name");
    private final NumberField professorName = new NumberField("Professor Id");

    private Dialog createDialog;
    private ConfirmDialog deleteDialog;

    public CourseList(CourseListPresenter presenter) {
        this.presenter = presenter;
        setSizeFull();

        buildGrid();
        Button addButton = new Button("Add Course", event -> openCreateDialog());
        addButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        H2 title = new H2("Courses");
        HorizontalLayout addBar = new HorizontalLayout();
        addBar.setWidthFull();

        addBar.add(title);
        addBar.addAndExpand(new Div()); // spacer
        addBar.add(addButton);
        add(addBar, grid);

        presenter.init(this);
    }

    private void openCreateDialog() {
        courseName.clear();
        professorName.clear();
        clearFieldError();

        createDialog = new Dialog();
        createDialog.setHeaderTitle("Create Professor");
        createDialog.add(new VerticalLayout(courseName, professorName));
        Button save =  new Button("Save", event -> presenter.onCreateClicked(
                courseName.getValue(),
                professorName.getValue().longValue()
        ));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancel = new Button("Cancel", event -> createDialog.close());

        createDialog.getFooter().add(cancel, save);
        createDialog.open();
    }

    private void buildGrid() {
        grid.addColumn(CourseResponse::id).setHeader("ID").setSortable(true);
        grid.addColumn(CourseResponse::courseName).setHeader("Course Name").setSortable(true);
        grid.addColumn(CourseResponse::professorName).setHeader("Professor Name").setSortable(true);
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
        deleteDialog.setHeader("Delete Course");

        deleteDialog.setText("Are you sure you want to delete this course?");
        deleteDialog.setCancelable(true);
        deleteDialog.setCancelButton("Close", event -> deleteDialog.close());
        Button confirm = new Button("Delete", event -> presenter.onDeleteClicked(id));
        confirm.addThemeVariants(ButtonVariant.PRIMARY, ButtonVariant.ERROR);
        deleteDialog.setConfirmButton(confirm);

        deleteDialog.open();
    }


    @Override
    public void show(List<CourseResponse> courses) {
        grid.setItems(courses);
    }

    @Override
    public void showFieldError(String field, String message) {

    }

    @Override
    public void clearFieldError() {
        courseName.setInvalid(false);
        professorName.setInvalid(false);
    }

    @Override
    public void showSuccess(String message) {
        Notification.show(message).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    @Override
    public void showError(String message) {
        Notification.show(message).addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    @Override
    public void closeCreatedDialog() {
        if(createDialog != null ){
            createDialog.close();
        }
    }

    @Override
    public void navigateToDetails(Long id) {

    }
}
