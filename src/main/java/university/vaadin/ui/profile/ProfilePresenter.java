package university.vaadin.ui.profile;

import university.vaadin.backend.dto.course.CourseResponse;

import java.util.List;
import java.util.Map;


public abstract class ProfilePresenter<E> {

    protected ProfileView view;

    protected abstract String firstName();
    protected abstract Map<String , String > details();
    protected abstract List<CourseResponse> courses();
    protected abstract E profile();

    public void init(ProfileView view){
        this.view = view;
        load();
    }

    protected void load(){

        view.showWelcome(firstName());
        view.showDetails(details());
        view.showCourses(courses());
    }


}
