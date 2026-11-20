package university.vaadin.ui.admin.professor.profile;

import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.ui.common.view.BaseView;

import java.util.List;
import java.util.Map;

public interface ProfessorView extends BaseView {

    void showProfessor(ProfessorResponse professor);
    void showNotFound(String message);
    void navigateBack();
    void navigateToCourse(Long courseId);
    void closeCreatedDialog();
    void showDetails(Map<String,String> details);
    void showCourses(List<CourseResponse> courses);
}
