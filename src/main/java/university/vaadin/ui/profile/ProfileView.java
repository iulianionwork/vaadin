package university.vaadin.ui.profile;

import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.ui.common.view.BaseView;

import java.util.List;
import java.util.Map;

public interface ProfileView extends BaseView {
    void showWelcome(String firstName);
    void showDetails(Map<String,String> details);
    void showCourses(List<CourseResponse> courses, boolean withDetailes);
    void navigateToCourse(Long id);
}
