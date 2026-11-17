package university.vaadin.ui.student;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.service.CourseService;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StudentCoursePresenter {

    private final CourseService courseService;

    public void init(StudentCourseView view){
        view.showCourses(courseService.getCoursesOfStudent());
    }
}
