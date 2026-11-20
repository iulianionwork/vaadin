package university.vaadin.ui.common.page;

import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import university.vaadin.ui.common.view.BaseView;
import university.vaadin.ui.common.Notifications;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BasePage extends VerticalLayout implements BaseView {

    private final Map<String, HasValidation> fields = new LinkedHashMap<>();


    protected void registerField(String fieldName, HasValidation validation) {
        fields.put(fieldName, validation);
    }


    protected void cleanForm(){
        for(HasValidation field : fields.values()){
            if(field instanceof HasValue<?, ?> value){
                value.clear();
            }
        }
        clearFieldError();
    }

    @Override
    public void showSuccess(String message) {
        Notifications.success(message);
    }

    @Override
    public void showError(String message) {
        Notifications.error(message);
    }

    @Override
    public void showFieldError(String field, String message) {
        HasValidation validation = fields.get(field);

        if(validation == null){
            showError(message);
            return;
        }

        validation.setErrorMessage(message);
        validation.setInvalid(true);

    }

    @Override
    public void clearFieldError() {
        fields.values().forEach(field -> field.setInvalid(false));

    }


}
