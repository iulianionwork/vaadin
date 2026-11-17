package university.vaadin.ui.admin.group;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.ui.common.ListView;
import university.vaadin.ui.common.Notifications;
import university.vaadin.ui.layout.MainLayout;

import java.util.List;

@Route(value = "admin/groups", layout = MainLayout.class)
@PageTitle("Groups")
@RolesAllowed("ADMIN")
public class GroupList extends VerticalLayout implements ListView<GroupResponse> {

    private final GroupListPresenter presenter;
    private final Grid<GroupResponse> grid = new Grid<>(GroupResponse.class, false);
    private final TextField groupName = new TextField("Group Name");

    private Dialog createDialog;
    private ConfirmDialog deleteDialog;

    public GroupList(GroupListPresenter presenter) {
        this.presenter = presenter;
        setSizeFull();

        buildGrid();
        Button addButton = new Button("Add Group", event -> openCreateDialog());
        addButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        H2 title = new H2("Groups");
        HorizontalLayout addBar = new HorizontalLayout();
        addBar.setWidthFull();

        addBar.add(title);
        addBar.addAndExpand(new Div()); // spacer
        addBar.add(addButton);
        add(addBar, grid);

        presenter.init(this);
    }

    private void openCreateDialog() {
        groupName.clear();
        clearFieldError();

        createDialog = new Dialog();
        createDialog.setHeaderTitle("Create Group");
        createDialog.add(new VerticalLayout(groupName));
        Button save =  new Button("Save", event -> presenter.onCreateClicked(
                groupName.getValue()
        ));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancel = new Button("Cancel", event -> createDialog.close());

        createDialog.getFooter().add(cancel, save);
        createDialog.open();
    }

    private void buildGrid() {
        grid.addColumn(GroupResponse::id).setHeader("ID").setSortable(true);
        grid.addColumn(GroupResponse::groupName).setHeader("Group Name").setSortable(true);
        grid.addColumn(GroupResponse::studentCount).setHeader("Number Of Students").setSortable(true);
        grid.addComponentColumn(s -> new Button("Details", e -> presenter.onDetailsClicked(s.id())));
        grid.addComponentColumn(s -> {
            Button delete = new Button("Delete", e-> confirmDialog(s.id()));
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            return delete;
        });

        grid.setSizeFull();
    }

    private void confirmDialog(Long id) {
        deleteDialog = new ConfirmDialog();
        deleteDialog.setHeader("Delete Group");

        deleteDialog.setText("Are you sure you want to delete this group?");
        deleteDialog.setCancelable(true);
        deleteDialog.setCancelButton("Close", event -> deleteDialog.close());
        Button confirm = new Button("Delete", event -> presenter.onDeleteClicked(id));
        confirm.addThemeVariants(ButtonVariant.PRIMARY, ButtonVariant.ERROR);
        deleteDialog.setConfirmButton(confirm);

        deleteDialog.open();
    }


    @Override
    public void show(List<GroupResponse> groups) {
        grid.setItems(groups);
    }

    @Override
    public void showFieldError(String field, String message) {

    }

    @Override
    public void clearFieldError() {
        groupName.setInvalid(false);
    }

    @Override
    public void showSuccess(String message) {
        Notifications.success(message);
    }

    @Override
    public void showError(String message) {
        Notifications.error(message);
    }

    @Override
    public void closeCreatedDialog() {
        if(createDialog != null ){
            createDialog.close();
        }
    }

    @Override
    public void navigateToDetails(Long id) {

    }
}
