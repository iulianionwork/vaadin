package university.vaadin.ui.professor;

import university.vaadin.backend.dto.student.StudentResponse;

import java.util.List;

public interface DetailsView<E> {

    void show(E entity);
    void navigateBack();
    void showStudents(List<StudentResponse> students);
}
