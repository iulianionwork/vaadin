package university.vaadin.ui.admin.professor.profile;

import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.ui.common.BaseView;
import university.vaadin.ui.profile.ProfileView;

public interface ProfessorView extends ProfileView, BaseView {

    void showProfessor(ProfessorResponse professor);
    void showNotFound(String message);
    void navigateBack();
    void closeCreatedDialog();
}
