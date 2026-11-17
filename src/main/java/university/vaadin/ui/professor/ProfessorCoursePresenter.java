package university.vaadin.ui.professor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.service.CourseService;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class ProfessorCoursePresenter {

    private final CourseService courseService;
    private ProfessorCourseView view;

    public void init(ProfessorCourseView view){
        this.view=view;
        view.showCourses(courseService.getCoursesOfProfessor());

    }

    public void onCourseClick(Long courseId){
        view.navigateToCourse(courseId);
    }
}
