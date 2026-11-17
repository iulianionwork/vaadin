package university.vaadin.ui.admin.professor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.professor.CreateProfessorRequest;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.backend.dto.professor.UpdateProfessorRequest;
import university.vaadin.backend.service.ProfessorService;
import university.vaadin.ui.common.ListView;

import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class ProfessorListPresenter {

    private final ProfessorService professorService;
    private final Validator validator;

    private ListView<ProfessorResponse> view;


    public void init(ListView<ProfessorResponse>  view) {
        this.view = view;
        loadProfessor();
    }

    private void loadProfessor() {
        view.show(professorService.getProfessors());
    }

    public void onCreateClicked(String firstName, String lastName, String password, String department) {
        CreateProfessorRequest request = new CreateProfessorRequest(firstName, lastName, password, department);
        view.clearFieldError();
        Set<ConstraintViolation<CreateProfessorRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<CreateProfessorRequest> violation : violations) {
                view.showFieldError(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return;
        }

        try{
            professorService.create(request);
            view.closeCreatedDialog();
            view.showSuccess("Professor successfully created");
            loadProfessor();
        }catch (RuntimeException ex){
            view.showError(ex.getMessage());
        }
    }

    public void onUpdateClicked(String firstName, String lastName, String department, Long professorId) {
        try {
            professorService.updateProfessor(professorId,
                    new UpdateProfessorRequest(firstName, lastName, department));
            view.closeCreatedDialog();
        }catch (RuntimeException ex){
            view.showError(ex.getMessage());
        }
    }

    public void onDeleteClicked(Long professorId) {
        try {
            professorService.deleteProfessor(professorId);
            view.showSuccess("Professor successfully deleted");
            loadProfessor();
        }catch (RuntimeException ex){
            view.showError(ex.getMessage());
        }
    }

    public void onDetailsClicked(Long professorId) {
        view.navigateToDetails(professorId);
    }

}
