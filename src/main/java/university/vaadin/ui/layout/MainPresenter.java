package university.vaadin.ui.layout;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.service.SecurityService;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class MainPresenter {

    private final SecurityService securityService;

    public void init(MainView view){
        view.showUserName(securityService.getUsername());

        if(securityService.hasRole("ADMIN")){
            view.showAdminMenu();
        } else if(securityService.hasRole("STUDENT")){
            view.showStudentMenu();
        } else if(securityService.hasRole("PROFESSOR")){
            view.showProfessorMenu();
        }
    }

    public void onLogoutClick(){
        securityService.logout();
    }


}
