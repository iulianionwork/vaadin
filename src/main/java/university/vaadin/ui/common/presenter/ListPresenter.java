package university.vaadin.ui.common.presenter;

import university.vaadin.ui.common.view.ListView;

import java.util.List;


public abstract class ListPresenter<E,R> extends BasePresenter<ListView<E>>  {

    protected abstract List<E> findAll();
    protected abstract void create(R request);
    protected abstract void delete(Long id);


    public void onCreateClicked(R request) {

        if (isValid(request) && execute(() -> create(request), "Successfully created!")) {
            view.closeCreatedDialog();
            load();
        }


    }

    @Override
    public void onInit(){
        load();
    }

    public void onDeleteClicked(Long id) {

        if(execute(() -> delete(id), "Successfully deleted!")){
            load();
        }
    }

    public void onDetailsClicked(Long id) {
        view.navigateToDetails(id);
    }

    protected void load(){
        view.show(findAll());
    }


}
