package university.vaadin.ui.common.view;

public interface BaseView {

    void showSuccess(String message);
    void showError(String message);
    void showFieldError(String field, String message);
    void clearFieldError();

}
