package university.vaadin.ui.admin.student.profile;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
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
import university.vaadin.backend.dto.carnet.CarnetResponse;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.backend.dto.student.UpdateStudentRequest;
import university.vaadin.ui.admin.course.profile.CoursePage;
import university.vaadin.ui.admin.student.list.StudentsPage;
import university.vaadin.ui.layout.MainPage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Route(value = "admin/students/detail", layout = MainPage.class)
@PageTitle("Student Details")
@RolesAllowed("ADMIN")
public class StudentPage extends VerticalLayout  implements StudentView, HasUrlParameter<Long> {


    private final StudentPresenter presenter;
    private final H1 welcome = new H1();
    private final FormLayout form = new FormLayout();
    private final FormLayout editForm = new FormLayout();
    private final Grid<CourseResponse> courseGrid = new Grid<>(CourseResponse.class, false);
    private final ComboBox<CourseResponse> courseBox = new ComboBox<>();

    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final ComboBox<GroupResponse> group = new ComboBox<>("Group");

    private final Map<String, HasValidation> fields = new LinkedHashMap<>();
    private Dialog createDialog;

    private final Span carnetText = new Span();
    private final Button issueCarnet = new Button("Issue Carnet");

    public StudentPage(StudentPresenter presenter) {
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
        group.setItemLabelGenerator(GroupResponse::groupName);

        editForm.add(firstName, lastName, group);

        courseGrid.addColumn(CourseResponse::courseName).setHeader("Course");
        courseGrid.addColumn(CourseResponse::professorName).setHeader("Professor");
        courseGrid.addComponentColumn(c -> {
            Button unenroll = new Button("Unenroll", e -> presenter.onUnenrollClicked(c.id()));
            unenroll.setThemeName("danger");
            return unenroll;
        });
        courseGrid.setWidthFull();

        issueCarnet.addClickListener(e -> presenter.onIssueCarnetClicked());
        issueCarnet.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        HorizontalLayout carnetRow = new HorizontalLayout(carnetText, issueCarnet);
        carnetRow.setAlignItems(Alignment.CENTER);

        courseBox.setPlaceholder("Choose a course");
        courseBox.setItemLabelGenerator(c -> c.courseName() + "(" + c.professorName() + ")");
        courseBox.setWidth("400px");
        Button enroll = new Button("Enroll", e -> presenter.onEnrollClicked(
                courseBox.getValue() == null ? null : courseBox.getValue().id()));
        enroll.addThemeVariants(ButtonVariant.LUMO_SUCCESS);



        VerticalLayout profile =  new VerticalLayout(new HorizontalLayout(new H2("Profile:"), edit), form, new H2("Carnet:"), carnetRow);
        VerticalLayout course =  new VerticalLayout( new H2("Courses:"),new HorizontalLayout(courseBox, enroll), courseGrid);
        HorizontalLayout content = new HorizontalLayout(profile,course);
        content.setWidthFull();


        add(header, content);
        presenter.init(this);
    }

    private void openEditDialog() {
        createDialog = new Dialog();
        createDialog.setHeaderTitle("Update student");
        createDialog.add(editForm);
        group.setItemLabelGenerator(GroupResponse::groupName);
        Button save =  new Button("Save", event -> {
            presenter.onSaveClicked(buildRequest(),group.getValue() == null ? null : group.getValue().id());
            closeCreatedDialog();
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancel = new Button("Cancel", event -> createDialog.close());

        createDialog.getFooter().add(cancel, save);
        createDialog.open();
    }

    @Override
    public void showWelcome(String firstName) {
        welcome.setText("Student Profile");
    }

    @Override
    public void showDetails(Map<String, String> details) {
        form.removeAll();
        details.forEach((label, value) -> {
            TextField field = new TextField(label);
            field.setValue(value);
            field.setReadOnly(true);

            form.add(field);});

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
    public void showGroups(List<GroupResponse> groups, GroupResponse current) {
        group.setItems(groups);
        group.setValue(current);
    }

    @Override
    public void showAvailableCourses(List<CourseResponse> courses) {
        courseBox.setItems(courses);
        courseBox.clear();
    }

    @Override
    public void showCarnet(CarnetResponse carnet) {
        carnetText.setText("Nr " + carnet.carnetNumber() + " , issue on " + carnet.issueDate());
        issueCarnet.setVisible(false);
    }

    @Override
    public void showNoCarnet() {
        carnetText.setText("No carnet");
        issueCarnet.setVisible(true);
    }

    @Override
    public void showNotFound(String message) {

    }

    @Override
    public void navigateBack() {
        UI.getCurrent().navigate(StudentsPage.class);
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
        switch(field){
            case "firstName" -> markInvalid(firstName, message);
            case "lastName" -> markInvalid(lastName, message);
            default -> showError(message);
        }
    }

    @Override
    public void clearFieldError() {
        firstName.setInvalid(false);
        lastName.setInvalid(false);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        presenter.load(aLong);
    }

    private void markInvalid(HasValidation field, String message) {
        field.setErrorMessage(message);
        field.setInvalid(true);
    }

    private UpdateStudentRequest buildRequest() {
        return new UpdateStudentRequest(firstName.getValue(), lastName.getValue());
    }

    public void closeCreatedDialog() {
        if(createDialog != null ){
            createDialog.close();
        }
    }
}
