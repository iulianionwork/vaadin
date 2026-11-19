package university.vaadin.ui.admin.professor.profile;

import university.vaadin.ui.common.BaseView;
import university.vaadin.ui.profile.ProfileView;

public interface ProfessorView extends ProfileView, BaseView {

    void showNotFound(String message);
    void navigateBack();
}
