package university.vaadin.ui.admin.course;

import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.ui.common.ListView;

import java.util.List;

public interface CoursePresenter extends ListView<CourseResponse> {

    void showProfessors(List<ProfessorResponse> professors);
}
