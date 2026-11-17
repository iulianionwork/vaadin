package university.vaadin.ui.layout;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "", layout = MainLayout.class)
@PageTitle("University")
@PermitAll
public class HomeView extends VerticalLayout {

    public HomeView() {}
}
