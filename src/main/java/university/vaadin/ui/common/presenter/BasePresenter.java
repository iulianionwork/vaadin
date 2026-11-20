package university.vaadin.ui.common.presenter;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import university.vaadin.ui.common.view.BaseView;

import java.util.Set;

public abstract class BasePresenter<V extends BaseView> {

    protected V view;
    private Validator validator;

    @Autowired
    void setValidator(Validator validator){
        this.validator = validator;
    }

    public void init(V view){
        this.view = view;
        onInit();
    }

    protected void onInit(){

    }

    protected  boolean isValid(Object request) {
        view.clearFieldError();
        Set<ConstraintViolation<Object>> errors = validator.validate(request);
            for (ConstraintViolation<Object> error : errors) {
                view.showFieldError(error.getPropertyPath().toString(), error.getMessage());
            }
        return errors.isEmpty();

    }

    protected boolean execute(Runnable action, String successMessage){
        try{
            action.run();
            view.showSuccess(successMessage);
            return true;
        }catch (RuntimeException e){
            view.showError(e.getMessage());
            return false;
        }
    }

}
