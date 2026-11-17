package university.vaadin.ui.admin.course;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.course.CourseRequest;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.course.CourseUpdate;
import university.vaadin.backend.service.CourseService;
import university.vaadin.ui.common.ListView;

import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class CourseListPresenter {

    private final CourseService courseService;
    private final Validator validator;

    private ListView<CourseResponse> view;


    public void init(ListView<CourseResponse> view) {
        this.view = view;
        loadCourses();
    }

    private void loadCourses() {
        view.show(courseService.getAllCourses());
    }

    public void onCreateClicked(String courseName, Long professorId) {
        CourseRequest request = new CourseRequest(courseName, professorId);
        view.clearFieldError();
        Set<ConstraintViolation<CourseRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<CourseRequest> violation : violations) {
                view.showFieldError(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return;
        }

        try{
            courseService.createCourse(request);
            view.closeCreatedDialog();
            view.showSuccess("Course successfully created");
            loadCourses();
        }catch (RuntimeException ex){
            view.showError(ex.getMessage());
        }
    }

    public void onUpdateClicked(Long professorId, Long courseId) {
        try {
            courseService.updateCourseProfessor(new CourseUpdate(professorId), courseId);
            view.closeCreatedDialog();
        }catch (RuntimeException ex){
            view.showError(ex.getMessage());
        }
    }

    public void onDeleteClicked(Long courseId) {
        try {
            courseService.deleteCourse(courseId);
            view.showSuccess("Course successfully deleted");
            loadCourses();
        }catch (RuntimeException ex){
            view.showError(ex.getMessage());
        }
    }

    public void onDetailsClicked(Long courseId) {
        view.navigateToDetails(courseId);
    }
}
