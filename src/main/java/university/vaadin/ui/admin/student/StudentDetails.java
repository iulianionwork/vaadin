package university.vaadin.ui.admin.student;

import university.vaadin.backend.dto.carnet.CarnetResponse;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.ui.common.BaseView;

import java.util.List;

public interface StudentDetails extends BaseView {

    void showStudent(StudentResponse student);
    void showGroups(List<GroupResponse> groups, GroupResponse current);
    void showEnrolledCourses(List<CourseResponse> courses);
    void showAvailableCourses(List<CourseResponse> courses);
    void showCarnet(CarnetResponse carnet);
    void showNoCarnet();
    void showNotFound(String message);
    void navigateBack();
}
