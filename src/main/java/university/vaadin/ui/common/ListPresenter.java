package university.vaadin.ui.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;


public abstract class ListPresenter<E,R>  {

    private final Validator validator;
    protected ListView<E> view;

    protected ListPresenter(Validator validator) {
        this.validator = validator;
    }

    protected abstract List<E> findAll();
    protected abstract void create(R request);
    protected abstract void delete(Long id);

    public void init(ListView<E> view) {
        this.view = view;
        load();
    }

    public void onCreateClicked(R request) {
        view.clearFieldError();
        Set<ConstraintViolation<R>> errors = validator.validate(request);
        if (!errors.isEmpty()) {
            for (ConstraintViolation<R> error : errors) {
                view.showFieldError(error.getPropertyPath().toString(), error.getMessage());
            }
            return;
        }
        try{
            create(request);
            view.closeCreatedDialog();
            view.showSuccess("Successfully created");
            load();
        }catch (RuntimeException ex){
            view.showError(ex.getMessage());
        }

    }

    public void onDeleteClicked(Long id) {
        try{
            delete(id);
            view.showSuccess("Successfully deleted");
            load();
        }catch (RuntimeException ex){
            view.showError(ex.getMessage());
        }
    }

    public void onDetailsClicked(Long id) {
        view.navigateToDetails(id);
    }

    protected void load(){
        view.show(findAll());
    }


}
