package university.vaadin.ui.admin.group.list;

import lombok.RequiredArgsConstructor;
import university.vaadin.backend.dto.group.GroupRequest;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.backend.service.GroupService;
import university.vaadin.ui.common.annotation.Presenter;
import university.vaadin.ui.common.presenter.ListPresenter;

import java.util.List;

@Presenter
@RequiredArgsConstructor
public class GroupsPresenter extends ListPresenter<GroupResponse, GroupRequest> {

    private final GroupService groupService;

    @Override
    protected List<GroupResponse> findAll() {
        return groupService.getGroups();
    }

    @Override
    protected void create(GroupRequest request) {
        groupService.createGroup(request);
    }

    @Override
    protected void delete(Long id) {
        groupService.deleteGroup(id);
    }
}
