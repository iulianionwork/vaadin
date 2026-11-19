package university.vaadin.ui.professor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.exception.NotFoundException;
import university.vaadin.backend.service.CourseService;
import university.vaadin.ui.common.DetailsView;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class ProfessorCoursePresenter {

    private final CourseService courseService;
    private DetailsView<CourseResponse> view;

    public void init(DetailsView<CourseResponse> view){
        this.view=view;

    }

    public void load(Long courseId) {
        try {
            List<StudentResponse> students = courseService.getStudentsOfMyCourse(courseId);
            view.show(courseService.getCourseById(courseId));
            view.showStudents(students);

        }catch (NotFoundException | AccessDeniedException e){
            view.showError(e.getMessage());
        }
    }

    public void onBackClicked(){
        view.navigateBack();
    }
}
