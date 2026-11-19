package university.vaadin.ui.common;

import university.vaadin.backend.dto.student.StudentResponse;

import java.util.List;

public interface DetailsView<E> {

    void show(E entity);
    void navigateBack();
    void showStudents(List<StudentResponse> students);
    void showError(String message);
}
