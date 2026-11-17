package university.vaadin.ui.admin.group;

import jakarta.validation.Validator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.group.GroupRequest;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.backend.service.GroupService;
import university.vaadin.ui.common.AbstractListPresenter;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GroupListPresenter extends AbstractListPresenter<GroupResponse, GroupRequest> {

    private final GroupService groupService;


    public GroupListPresenter(GroupService groupService, Validator validator) {
        super(validator);
        this.groupService = groupService;
    }


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
