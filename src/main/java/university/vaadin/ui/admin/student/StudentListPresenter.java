package university.vaadin.ui.admin.student;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.student.CreateStudentRequest;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.service.StudentService;
import university.vaadin.ui.common.ListView;

import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StudentListPresenter {

    private final StudentService studentService;
    private final Validator validator;

    private ListView<StudentResponse> view;

    public void init(ListView<StudentResponse> view) {
        this.view = view;
        loadStudents();
    }



    public void onCreateClicked(String firstName, String lastName, String password) {
        CreateStudentRequest request = new CreateStudentRequest(firstName, lastName, password);
        view.clearFieldError();
        Set<ConstraintViolation<CreateStudentRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<CreateStudentRequest> violation : violations) {
                view.showFieldError(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return;
        }
        try{
            studentService.create(request);
            view.closeCreatedDialog();
            view.showSuccess("Student successful created");
            loadStudents();
        }catch (RuntimeException ex){
            view.showError(ex.getMessage());
        }

    }

    public void onDeleteClicked(Long studentId) {
        try{
            studentService.deleteStudent(studentId);
            view.showSuccess("Student successfully deleted");
            loadStudents();
        }catch (RuntimeException ex){
            view.showError(ex.getMessage());
        }
    }


    public void onDetailsClicked(Long studentId) {
        view.navigateToDetails(studentId);
    }

    private void loadStudents() {
        view.show(studentService.getStudents());
    }


}
