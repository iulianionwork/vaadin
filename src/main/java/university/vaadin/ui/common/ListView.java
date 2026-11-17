package university.vaadin.ui.common;

import java.util.List;

public interface ListView<E>  extends BaseView {

    void closeCreatedDialog();
    void navigateToDetails(Long id);
    void show(List<E> items);
}
