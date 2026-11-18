package university.vaadin.ui.profile;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.backend.service.CourseService;
import university.vaadin.backend.service.UserService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProfessorProfile extends ProfilePresenter<ProfessorResponse> {

    private final UserService userService;
    private final CourseService courseService;
    private ProfileProfessorView view;

    public ProfessorProfile(UserService userService, CourseService courseService){
        this.courseService = courseService;
        this.userService = userService;
    }

    public void init(ProfileProfessorView view){
        super.init(view);
    }



    @Override
    protected String firstName() {
        return profile().firstName();
    }

    @Override
    protected Map<String, String> details() {
        ProfessorResponse professor = profile();
        HashMap<String, String> fields = new LinkedHashMap<>();
        fields.put("First Name", professor.firstName());
        fields.put("Last Name", professor.lastName());
        fields.put("Email", professor.email());
        fields.put("Department", professor.department());
        return fields;
    }

    @Override
    protected List<CourseResponse> courses() {
        return courseService.getCoursesOfProfessor();
    }

    @Override
    protected ProfessorResponse profile() {
        return userService.getCurrentProfessor();
    }

    public void onCourseClick(Long id){
        view.navigateToCourse(id);
    }


}
