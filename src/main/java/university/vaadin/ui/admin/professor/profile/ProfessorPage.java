package university.vaadin.ui.admin.professor.profile;

import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.professor.UpdateProfessorRequest;
import university.vaadin.ui.admin.course.profile.CoursePage;
import university.vaadin.ui.admin.professor.list.ProfessorsPage;
import university.vaadin.ui.layout.MainPage;

import java.util.List;
import java.util.Map;

@Route(value = "admin/professors/detail", layout = MainPage.class)
@PageTitle("Professor Details")
@RolesAllowed("ADMIN")
public class ProfessorPage extends VerticalLayout implements ProfessorView, HasUrlParameter<Long> {


    private final ProfessorPresenter presenter;
    private final H1 welcome = new H1();
    private final FormLayout form = new FormLayout();
    private final FormLayout editForm = new FormLayout();
    private final Grid<CourseResponse> courseGrid = new Grid<>(CourseResponse.class, false);


    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final TextField department = new TextField("Department");


    private Dialog createDialog;


    public ProfessorPage(ProfessorPresenter presenter) {
        this.presenter = presenter;

        form.setMaxWidth("600px");

        Button back = new Button("← Back to list", e -> presenter.onBackClicked());
        back.addClickShortcut(Key.ESCAPE);
        back.addThemeVariants(ButtonVariant.LUMO_WARNING);

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();

        header.add(welcome);
        header.addAndExpand(new Div()); // spacer
        header.add(back);

        Button edit = new Button("Edit", e -> openEditDialog());
        back.addClickShortcut(Key.ESCAPE);


        editForm.add(firstName, lastName, department);

        courseGrid.addColumn(CourseResponse::courseName).setHeader("Course");
        courseGrid.addComponentColumn(s -> new Button("Details", e -> presenter.onCourseClicked(s.id())));
        courseGrid.setWidthFull();


        VerticalLayout profile = new VerticalLayout(new HorizontalLayout(new H2("Profile:"), edit), form);
        VerticalLayout course = new VerticalLayout(new H2("Courses:"), courseGrid);
        HorizontalLayout content = new HorizontalLayout(profile, course);
        content.setWidthFull();


        add(header, content);
        presenter.init(this);
    }

    private void openEditDialog() {
        createDialog = new Dialog();
        createDialog.setHeaderTitle("Update student");
        createDialog.add(editForm);
        Button save = new Button("Save", event -> {
            presenter.onSaveClicked(buildRequest());
            closeCreatedDialog();
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancel = new Button("Cancel", event -> createDialog.close());

        createDialog.getFooter().add(cancel, save);
        createDialog.open();
    }

    @Override
    public void showWelcome(String firstName) {
        welcome.setText("Professor Profile");
    }

    @Override
    public void showDetails(Map<String, String> details) {
        form.removeAll();
        details.forEach((label, value) -> {
            TextField field = new TextField(label);
            field.setValue(value);
            field.setReadOnly(true);

            form.add(field);
        });

    }

    @Override
    public void showCourses(List<CourseResponse> courses, boolean withDetails) {
        courseGrid.setItems(courses);
    }

    @Override
    public void navigateToCourse(Long id) {
        UI.getCurrent().navigate(CoursePage.class, id);
    }


    @Override
    public void showNotFound(String message) {

    }

    @Override
    public void navigateBack() {
        UI.getCurrent().navigate(ProfessorsPage.class);
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
    public void showFieldError(String field, String message) {
        switch (field) {
            case "firstName" -> markInvalid(firstName, message);
            case "lastName" -> markInvalid(lastName, message);
            case "department" -> markInvalid(department, message);
            default -> showError(message);
        }
    }

    @Override
    public void clearFieldError() {
        firstName.setInvalid(false);
        lastName.setInvalid(false);
        department.setInvalid(false);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        presenter.load(aLong);
    }

    private void markInvalid(HasValidation field, String message) {
        field.setErrorMessage(message);
        field.setInvalid(true);
    }

    private UpdateProfessorRequest buildRequest() {
        return new UpdateProfessorRequest(firstName.getValue(), lastName.getValue(), department.getValue());
    }

    public void closeCreatedDialog() {
        if (createDialog != null) {
            createDialog.close();
        }
    }
}