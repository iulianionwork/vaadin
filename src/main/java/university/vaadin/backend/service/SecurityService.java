package university.vaadin.backend.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityService {

    public boolean hasRole(String role) {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth == null){
            return false;
        }

        for(GrantedAuthority ga : auth.getAuthorities()){
            if(ga.getAuthority().equals("ROLE_"+role)){
                return true;
            }
        }
        return false;
    }

    public String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth == null ? "" : auth.getName();
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation("/login");
        new SecurityContextLogoutHandler().logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }
}
