package university.vaadin.ui.admin.student.profile;

import university.vaadin.backend.dto.carnet.CarnetResponse;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.ui.common.view.BaseView;

import java.util.List;
import java.util.Map;

public interface StudentView extends BaseView {

    void closeCreatedDialog();
    void showStudent(StudentResponse student);
    void showGroups(List<GroupResponse> groups, GroupResponse current);
    void showAvailableCourses(List<CourseResponse> courses);
    void showCarnet(CarnetResponse carnet);
    void showNoCarnet();
    void showNotFound(String message);
    void navigateBack();
    void showDetails(Map<String,String> details);
    void showCourses(List<CourseResponse> courses);
}
