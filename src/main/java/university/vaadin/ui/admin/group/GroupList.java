package university.vaadin.ui.admin.group;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.group.GroupRequest;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.ui.admin.student.StudentDetailsImp;
import university.vaadin.ui.common.AbstractListView;
import university.vaadin.ui.layout.MainLayout;

@Route(value = "admin/groups", layout = MainLayout.class)
@PageTitle("Groups")
@RolesAllowed("ADMIN")
    public class GroupList extends AbstractListView<GroupResponse, GroupRequest> {

    private final TextField groupName = new TextField("Group Name");

    public GroupList(GroupListPresenter presenter) {
            super("Groups",
                "group",
                GroupResponse.class,
                presenter,
                GroupResponse::id,
                id -> UI.getCurrent().navigate(StudentDetailsImp.class, id));
        registerFiled("Name", groupName);
        build();
        presenter.init(this);
    }



    @Override
    protected void configureColumns(Grid<GroupResponse> grid) {
        grid.addColumn(GroupResponse::id).setHeader("ID").setSortable(true);
        grid.addColumn(GroupResponse::groupName).setHeader("Group Name").setSortable(true);
        grid.addColumn(GroupResponse::studentCount).setHeader("Number Of Students").setSortable(true);
    }

    @Override
    protected Component createForm() {
        return new FormLayout(groupName);
    }

    @Override
    protected GroupRequest buildRequest() {
        return new GroupRequest(groupName.getValue());
    }
}
