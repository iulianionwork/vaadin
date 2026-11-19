package university.vaadin.ui.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.service.CourseService;
import university.vaadin.backend.service.SecurityService;
import university.vaadin.backend.service.UserService;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class ProfilePresenter {

    private final UserService userService;
    private final CourseService courseService;
    private final SecurityService securityService;
    private ProfileView view;
    private final  HashMap<String, String> details = new LinkedHashMap<>();


    public void init(ProfileView view){
        this.view = view;
         if(securityService.hasRole("STUDENT")){
             showStudent();
         } else{
             showProfessor();
         }
    }

    private void showProfessor() {
        ProfessorResponse professor = userService.getCurrentProfessor();

        view.showWelcome(professor.firstName());

        details.put("First Name", professor.firstName());
        details.put("Last Name", professor.lastName());
        details.put("Email", professor.email());
        details.put("Department", professor.department());
        view.showDetails(details);

        view.showCourses(courseService.getCoursesOfProfessor(), true);
    }

    private void showStudent() {
        StudentResponse student = userService.getCurrentStudent();

        view.showWelcome(student.firstName());


        details.put("First Name", student.firstName());
        details.put("Last Name", student.lastName());
        details.put("Email", student.email());
        details.put("Group", student.groupName() == null ? "You aren't assign to group yet!": student.groupName());
        view.showDetails(details);

        view.showCourses(courseService.getCoursesOfStudent(), false);

    }

    public void onCourseClicked(Long courseId){
        view.navigateToCourse(courseId);
    }




}
