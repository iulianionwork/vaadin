package university.vaadin.ui.professor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.service.CourseService;
import university.vaadin.ui.common.presenter.DetailsPresenter;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class ProfessorCoursePresenter extends DetailsPresenter<CourseResponse> {

    private final CourseService courseService;


    @Override
    protected CourseResponse findById(Long id) {
        return courseService.getCourseById(id);
    }

    @Override
    protected List<StudentResponse> findStudents(Long id) {
        return courseService.getStudentsOfMyCourse(id);
    }
}
