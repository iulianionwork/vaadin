package university.vaadin.ui.admin.course.list;


import lombok.RequiredArgsConstructor;
import university.vaadin.backend.dto.course.CourseRequest;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.service.CourseService;
import university.vaadin.backend.service.ProfessorService;
import university.vaadin.ui.common.annotation.Presenter;
import university.vaadin.ui.common.presenter.ListPresenter;

import java.util.List;

@Presenter
@RequiredArgsConstructor
public class CoursesPresenter extends ListPresenter<CourseResponse, CourseRequest> {

    private final CourseService courseService;
    private final ProfessorService professorService;

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
