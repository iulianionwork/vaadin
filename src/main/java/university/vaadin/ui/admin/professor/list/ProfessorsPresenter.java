package university.vaadin.ui.admin.professor.list;

import jakarta.validation.Validator;
import university.vaadin.backend.dto.professor.CreateProfessorRequest;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.backend.service.ProfessorService;
import university.vaadin.ui.common.annotation.Presenter;
import university.vaadin.ui.common.presenter.ListPresenter;

import java.util.List;

@Presenter
public class ProfessorsPresenter extends ListPresenter<ProfessorResponse, CreateProfessorRequest> {

    private final ProfessorService professorService;

    public ProfessorsPresenter(ProfessorService professorService, Validator validator) {
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
