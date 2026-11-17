package university.vaadin.ui.admin.professor;

import jakarta.validation.Validator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.professor.CreateProfessorRequest;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.backend.service.ProfessorService;
import university.vaadin.ui.common.AbstractListPresenter;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProfessorListPresenter extends AbstractListPresenter<ProfessorResponse, CreateProfessorRequest> {

    private final ProfessorService professorService;

    public ProfessorListPresenter(ProfessorService professorService, Validator validator) {
        super(validator);
        this.professorService = professorService;
    }

    @Override
    protected List<ProfessorResponse> findAll() {
        return professorService.getProfessors();
    }

    @Override
    protected void create(CreateProfessorRequest request) {
        professorService.create(request);
    }

    @Override
    protected void delete(Long id) {
        professorService.deleteProfessor(id);
    }
}
