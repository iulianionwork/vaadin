package university.vaadin.ui.admin.course.list;


import jakarta.validation.Validator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.course.CourseRequest;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.service.CourseService;
import university.vaadin.backend.service.ProfessorService;
import university.vaadin.ui.common.ListPresenter;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CoursesPresenter extends ListPresenter<CourseResponse, CourseRequest> {

    private final CourseService courseService;
    private final ProfessorService professorService;

    public CoursesPresenter(CourseService courseService, ProfessorService professorService, Validator validator) {
        super(validator);
        this.courseService = courseService;
        this.professorService = professorService;
    }

    public void init(CourseView view) {
        super.init(view);
        view.showProfessors(professorService.getProfessors());
    }

    @Override
    protected List<CourseResponse> findAll() {
        return courseService.getAllCourses();
    }

    @Override
    protected void create(CourseRequest request) {
        courseService.create(request);
    }

    @Override
    protected void delete(Long id) {
        courseService.delete(id);
    }
}
