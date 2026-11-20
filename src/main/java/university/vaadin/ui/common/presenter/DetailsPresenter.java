package university.vaadin.ui.common.presenter;

import org.springframework.security.access.AccessDeniedException;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.exception.NotFoundException;
import university.vaadin.ui.common.view.DetailsView;

import java.util.List;

public abstract class DetailsPresenter<E> extends BasePresenter<DetailsView<E>> {

    protected abstract E findById(Long id);
    protected abstract List<StudentResponse> findStudents(Long id);



    public void load(Long id) {
        try {
            List<StudentResponse> students = findStudents(id);
            view.show(findById(id));
            view.showStudents(students);

        }catch (NotFoundException | AccessDeniedException e){
            view.showError(e.getMessage());
            view.navigateBack();
        }
    }

    public void onBackClicked(){
        view.navigateBack();
    }
}
