package university.vaadin.ui.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    public LoginView() {

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginI18n loginI18n = new LoginI18n();
        LoginI18n.Form form = new LoginI18n.Form();

        form.setTitle("University");
        form.setUsername("Email");
        form.setPassword("Password");
        form.setSubmit("Login");

        LoginI18n.ErrorMessage error = new LoginI18n.ErrorMessage();


        error.setTitle("Error");
        error.setMessage("Invalid Username/Password");
        loginI18n.setForm(form);
        loginI18n.setErrorMessage(error);
        loginForm.setI18n(loginI18n);
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.setAction("login");
        add(loginForm);


    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(event.getLocation().getQueryParameters().getParameters().containsKey("error")){
            loginForm.setError(true);
        }
    }
}