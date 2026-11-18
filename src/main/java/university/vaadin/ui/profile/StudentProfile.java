package university.vaadin.ui.profile;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.service.CourseService;
import university.vaadin.backend.service.UserService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentProfile extends ProfilePresenter<StudentResponse>{


    private final UserService userService;
    private final CourseService courseService;



    public StudentProfile( UserService userService, CourseService courseService){
        this.userService = userService;
        this.courseService = courseService;

    }



    @Override
    protected String firstName() {
        return profile().firstName();
    }

    @Override
    protected Map<String, String> details() {
        StudentResponse student = profile();
        HashMap<String, String> fields = new LinkedHashMap<>();
        fields.put("First Name", student.firstName());
        fields.put("Last Name", student.lastName());
        fields.put("Email", student.email());
        fields.put("Group", student.groupName() == null ? "You aren't assign to group yet!": student.groupName());
        return fields;
    }

    @Override
    protected List<CourseResponse> courses() {
        return courseService.getCoursesOfStudent();
    }

    @Override
    protected StudentResponse profile() {
        return userService.getCurrentStudent();
    }
}
