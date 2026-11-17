package university.vaadin.ui.professor;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.ui.layout.MainLayout;

@Route(value = "teaching/course", layout = MainLayout.class)
@PageTitle("Courses")
@RolesAllowed({"PROFESSOR"})
public class ProfessorCourseDetailsViewImp extends VerticalLayout  implements HasUrlParameter<Long> {


    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {

    }
}
