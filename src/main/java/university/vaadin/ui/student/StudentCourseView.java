package university.vaadin.ui.student;

import university.vaadin.backend.dto.course.CourseResponse;

import java.util.List;

public interface StudentCourseView {

    void showCourses(List<CourseResponse> courses);
}
