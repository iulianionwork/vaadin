package university.vaadin.ui.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.service.SecurityService;
import university.vaadin.backend.service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class ProfilePresenter {

    private final SecurityService securityService;
    private final UserService userService;

    public void init(ProfileView view){
        Map<String,String> details = new LinkedHashMap<>();

        if(securityService.hasRole("STUDENT")){
            StudentResponse student = userService.getCurrentStudent();

            view.showWelcome(student.firstName());
            details.put("First Name", student.firstName());
            details.put("Last Name", student.lastName());
            details.put("Email", student.email());
            details.put("Group", student.groupName() == null ? "Not assigned yet" : student.groupName());
        }else{
            ProfessorResponse professor = userService.getCurrentProfessor();
            view.showWelcome(professor.firstName());
            details.put("First Name", professor.firstName());
            details.put("Last Name", professor.lastName());
            details.put("Email", professor.email());
            details.put("Department", professor.department());
        }

        view.showDetails(details);
    }
}
