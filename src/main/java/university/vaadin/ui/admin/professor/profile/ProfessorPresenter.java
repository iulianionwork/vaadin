package university.vaadin.ui.admin.professor.profile;


import lombok.RequiredArgsConstructor;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.backend.dto.professor.UpdateProfessorRequest;
import university.vaadin.backend.exception.NotFoundException;
import university.vaadin.backend.service.CourseService;
import university.vaadin.backend.service.ProfessorService;
import university.vaadin.ui.common.annotation.Presenter;
import university.vaadin.ui.common.presenter.BasePresenter;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Presenter
@RequiredArgsConstructor()
public class ProfessorPresenter extends BasePresenter<ProfessorView> {

    private final ProfessorService professorService;
    private final CourseService courseService;
    private final HashMap<String, String> details = new LinkedHashMap<>();
    private Long professorId;

    @Override
    public void onInit(){
        load(professorId);
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

        if(!isValid(request)){
            return;
        }


        if(execute(()->
            professorService.updateProfessor(professorId, request)
        ,"Successfully updated professor details" )){
            view.closeCreatedDialog();
            load(professorId);
        }


    }


    public void onCourseClicked(Long courseId){
        view.navigateToCourse(courseId);
    }

    public void onBackClicked() {
        view.navigateBack();
    }

}




