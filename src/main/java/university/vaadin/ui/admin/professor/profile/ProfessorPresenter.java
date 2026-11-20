package university.vaadin.ui.admin.professor.profile;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.backend.dto.professor.UpdateProfessorRequest;
import university.vaadin.backend.exception.NotFoundException;
import university.vaadin.backend.service.CourseService;
import university.vaadin.backend.service.ProfessorService;
import university.vaadin.ui.common.annotation.Presenter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

@Presenter
@RequiredArgsConstructor()
public class ProfessorPresenter {

    private final ProfessorService professorService;
    private final CourseService courseService;
    private final Validator validator;
    private final HashMap<String, String> details = new LinkedHashMap<>();

    private ProfessorView view;
    private Long professorId;


    public void init(ProfessorView view) {
        this.view = view;
    }

    public void load(Long professorId) {
        this.professorId = professorId;
        try{
            ProfessorResponse professor = professorService.getProfessorById(professorId);

            view.showWelcome(professor.firstName());

            details.put("First Name", professor.firstName());
            details.put("Last Name", professor.lastName());
            details.put("Email", professor.email());
            details.put("Department", professor.department());
            view.showDetails(details);
            view.showCourses(courseService.getCoursesOfProfessorById(professorId), true);
            view.showProfessor(professor);


        }catch(NotFoundException e){
            view.showNotFound(e.getMessage());
        }
    }


    public void onSaveClicked(UpdateProfessorRequest request) {
        view.clearFieldError();
        Set<ConstraintViolation<UpdateProfessorRequest>> errors = validator.validate(request);
        if(!errors.isEmpty()) {
            for(ConstraintViolation<UpdateProfessorRequest> error : errors) {
                view.showFieldError(error.getPropertyPath().toString(), error.getMessage());
            }
            return;
        }

        try{
            professorService.updateProfessor(professorId, request);
            view.closeCreatedDialog();
            load(professorId);
            view.showSuccess("Successfully updated professor details");
        }catch(RuntimeException e){
            view.showError(e.getMessage());
        }

    }


    public void onCourseClicked(Long courseId){
        view.navigateToCourse(courseId);
    }

    public void onBackClicked() {
        view.navigateBack();
    }

}




