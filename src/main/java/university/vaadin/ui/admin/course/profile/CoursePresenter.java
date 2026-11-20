package university.vaadin.ui.admin.course.profile;

import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.service.CourseService;
import university.vaadin.ui.common.annotation.Presenter;
import university.vaadin.ui.common.presenter.DetailsPresenter;

import java.util.List;

@Presenter
public class CoursePresenter extends DetailsPresenter<CourseResponse> {

    private final CourseService courseService;

    public CoursePresenter(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    protected CourseResponse findById(Long id) {
        return courseService.getCourseById(id);
    }

    @Override
    protected List<StudentResponse> findStudents(Long id) {
        return courseService.getStudentsByCourseId(id);
    }
}
