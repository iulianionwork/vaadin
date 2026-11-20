package university.vaadin.ui.common.view;

import university.vaadin.backend.dto.student.StudentResponse;

import java.util.List;

public interface DetailsView<E>  extends BaseView{

    void show(E entity);
    void navigateBack();
    void showStudents(List<StudentResponse> students);
}
