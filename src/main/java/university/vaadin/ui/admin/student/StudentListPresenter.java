package university.vaadin.ui.admin.student;

import jakarta.validation.Validator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.student.CreateStudentRequest;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.service.StudentService;
import university.vaadin.ui.common.AbstractListPresenter;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentListPresenter extends AbstractListPresenter<StudentResponse, CreateStudentRequest> {

    private final StudentService studentService;

    public StudentListPresenter(StudentService studentService, Validator validator){
        super(validator);
        this.studentService = studentService;
    }


    @Override
    protected List<StudentResponse> findAll() {
        return studentService.getStudents();
    }

    @Override
    protected void create(CreateStudentRequest request) {
        studentService.create(request);
    }

    @Override
    protected void delete(Long id) {
        studentService.deleteStudent(id);
    }
}
