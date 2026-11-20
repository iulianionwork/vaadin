package university.vaadin.ui.admin.group.profile;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.ui.admin.group.list.GroupsPage;
import university.vaadin.ui.common.page.DetailsPage;
import university.vaadin.ui.layout.MainPage;

@Route(value = "admin/groups/details", layout = MainPage.class)
@PageTitle("Group")
@RolesAllowed({"ADMIN"})
public class GroupPage extends DetailsPage<GroupResponse> {

    public GroupPage(GroupPresenter presenter) {
        super(presenter,"← Back to list");
    }

    @Override
    public void show(GroupResponse group) {
        title.setText(group.groupName());
        info.setText("Number of Students: " + group.studentCount());
    }

    @Override
    public void navigateBack() {
        UI.getCurrent().navigate(GroupsPage.class);
    }


}
