package university.vaadin.ui.professor;

import university.vaadin.backend.dto.course.CourseResponse;

import java.util.List;

public interface ProfessorCourseView {

    void showCourses(List<CourseResponse> courses);
    void navigateToCourse(Long id);
}
