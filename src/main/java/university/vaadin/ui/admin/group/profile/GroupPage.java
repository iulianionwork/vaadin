package university.vaadin.ui.admin.group.profile;


import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.ui.admin.group.list.GroupsPage;
import university.vaadin.ui.common.Notifications;
import university.vaadin.ui.layout.MainPage;
import university.vaadin.ui.common.DetailsView;

import java.util.List;

@Route(value = "admin/groups/details", layout = MainPage.class)
@PageTitle("Group")
@RolesAllowed({"ADMIN"})
public class GroupPage extends VerticalLayout implements DetailsView<GroupResponse>, HasUrlParameter<Long> {

    private final GroupPresenter presenter;
    private final H1 title = new H1();
    private final Span numberOfStudents = new Span();
    private final Grid<StudentResponse> grid = new Grid<>(StudentResponse.class, false);

    public GroupPage(GroupPresenter presenter) {
        this.presenter = presenter;

        Button back = new Button("← Back to list", e -> presenter.onBackClicked());
        back.addClickShortcut(Key.ESCAPE);
        back.addThemeVariants(ButtonVariant.LUMO_WARNING);

        grid.addColumn(StudentResponse::firstName).setHeader("First Name").setSortable(true);
        grid.addColumn(StudentResponse::lastName).setHeader("Last Name").setSortable(true);
        grid.addColumn(StudentResponse::email).setHeader("Email");

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();

        header.add(title);
        header.addAndExpand(new Div()); // spacer
        header.add(back);


        add(header, new Div(), new H3("Number of Students:"), numberOfStudents, new Div(), new H3("Students:"), grid);

        presenter.init(this);

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {
        presenter.load(aLong);
    }

    @Override
    public void show(GroupResponse group) {
        title.setText(group.groupName());
        numberOfStudents.setText(String.valueOf(group.studentCount()));
    }

    @Override
    public void navigateBack() {
        UI.getCurrent().navigate(GroupsPage.class);
    }

    @Override
    public void showStudents(List<StudentResponse> students) {
        grid.setItems(students);
    }

    @Override
    public void showError(String message) {
        Notifications.error(message);
    }

}
