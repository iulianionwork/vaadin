package university.vaadin.ui.admin.student.list;

import jakarta.validation.Validator;
import university.vaadin.backend.dto.student.CreateStudentRequest;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.service.StudentService;
import university.vaadin.ui.common.annotation.Presenter;
import university.vaadin.ui.common.presenter.ListPresenter;

import java.util.List;

@Presenter
public class StudentsPresenter extends ListPresenter<StudentResponse, CreateStudentRequest> {

    private final StudentService studentService;

    public StudentsPresenter(StudentService studentService, Validator validator){
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
