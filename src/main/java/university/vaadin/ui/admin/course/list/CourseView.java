package university.vaadin.ui.admin.course.list;

import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.ui.common.view.ListView;

import java.util.List;

public interface CourseView extends ListView<CourseResponse> {

    void showProfessors(List<ProfessorResponse> professors);
}
