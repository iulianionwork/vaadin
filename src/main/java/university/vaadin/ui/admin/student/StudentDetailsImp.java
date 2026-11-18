package university.vaadin.ui.admin.student;

import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
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
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.ui.layout.MainLayout;

import java.util.List;

@Route(value = "admin/students/detail", layout = MainLayout.class)
@PageTitle("Student Details")
@RolesAllowed("ADMIN")
public class StudentDetailsImp extends VerticalLayout implements StudentDetails, HasUrlParameter<Long> {

    private final StudentDetailsPresenter presenter;
    private final H2 title = new H2();
    private final TextField firstName = new TextField();
    private final TextField lastName = new TextField();
    private final TextField email = new TextField();
    private final ComboBox<GroupResponse> group = new ComboBox<>("Group");

    private final Span carnetText = new Span();
    private final Button issueCarnet = new Button("Issue Carnet");

    private final ComboBox<CourseResponse> courseBox = new ComboBox<>();
    private final Grid<CourseResponse> courseGrid = new Grid<>(CourseResponse.class, false);


    public StudentDetailsImp(StudentDetailsPresenter presenter) {
        this.presenter = presenter;
        email.setReadOnly(true);
        group.setItemLabelGenerator(GroupResponse::groupName);
        group.addValueChangeListener(e -> {
            if(e.isFromClient()){
                presenter.onGroupSelected(e.getValue() == null ? null : e.getValue().id());
            }
        });
        Button save = new Button("Save", e -> presenter.onSaveClicked(firstName.getValue(), lastName.getValue()));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        FormLayout form = new FormLayout(firstName, lastName, email, group);
        form.setWidth("600px");

        issueCarnet.addClickListener(e -> presenter.onIssueCarnetClicked());
        HorizontalLayout carnetRow = new HorizontalLayout(carnetText, issueCarnet);
        carnetRow.setAlignItems(Alignment.CENTER);

        courseBox.setPlaceholder("Choose a course");
        courseBox.setItemLabelGenerator(c -> c.courseName() + "(" + c.professorName() + ")");
        courseBox.setWidth("400px");
        Button enroll = new Button("Enroll", e -> presenter.onEnrollClicked(
                courseBox.getValue() == null ? null : courseBox.getValue().id()));
        Button back = new Button("← Back", e -> presenter.onBackClicked());
        back.addClickShortcut(Key.ESCAPE);

        courseGrid.addColumn(CourseResponse::courseName).setHeader("Course Name");
        courseGrid.addColumn(CourseResponse::professorName).setHeader("Professor Name");
        courseGrid.addComponentColumn(c -> {
            Button unenroll = new Button("Unenroll", e -> presenter.onUnenrollClicked(c.id()));
            unenroll.setThemeName("danger");
            return unenroll;
        });
        courseGrid.setAllRowsVisible(true);

        add(back,
                title,
                form,
                new H3("Carnet"), carnetRow,
                new H3("Courses"), new HorizontalLayout(courseBox, enroll), courseGrid);

        presenter.init(this);
    }
    @Override
    public void setParameter(BeforeEvent beforeEvent, Long studentId) {
        presenter.onStudentSelected(studentId);
    }

    @Override
    public void showStudent(StudentResponse student) {
        title.setText("Student " + student.firstName() + " " + student.lastName());
        firstName.setValue(student.firstName());
        lastName.setValue(student.lastName());
        email.setValue(student.email());
    }


    @Override
    public void showGroups(List<GroupResponse> groups, GroupResponse current) {
        group.setItems(groups);
        group.setValue(current);
    }

    @Override
    public void showEnrolledCourses(List<CourseResponse> courses) {
        courseGrid.setItems(courses);
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
        removeAll();
        add(new H2("Student Not Found"), new Paragraph(message),
                new Button("← Back to list", e -> presenter.onBackClicked()));
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
    public void navigateBack() {
        UI.getCurrent().navigate(StudentList.class);
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

    private void markInvalid(HasValidation field, String message) {
        field.setErrorMessage(message);
        field.setInvalid(true);
    }
}
