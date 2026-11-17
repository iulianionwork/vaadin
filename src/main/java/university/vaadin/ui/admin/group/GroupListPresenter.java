package university.vaadin.ui.admin.group;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.group.GroupRequest;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.backend.service.GroupService;
import university.vaadin.ui.common.ListView;

import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class GroupListPresenter {

    private final GroupService groupService;
    private final Validator validator;

    private ListView<GroupResponse> view;



    public void init(ListView<GroupResponse>  view) {
        this.view = view;
        loadGroups();
    }

    private void loadGroups() {
        view.show(groupService.getGroups());
    }

    public void onCreateClicked(String groupName) {
        GroupRequest request = new GroupRequest(groupName);
        view.clearFieldError();
        Set<ConstraintViolation<GroupRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<GroupRequest> violation : violations) {
                view.showFieldError(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return;
        }

        try{
            groupService.createGroup(request);
            view.closeCreatedDialog();
            view.showSuccess("Group successfully created");
            loadGroups();
        }catch (RuntimeException ex){
            view.showError(ex.getMessage());
        }
    }


    public void onDeleteClicked(Long id) {
        try {
            groupService.deleteGroup(id);
            view.showSuccess("Group successfully deleted");
            loadGroups();
        }catch (RuntimeException ex){
            view.showError(ex.getMessage());
        }
    }

    public void onDetailsClicked(Long id) {
        view.navigateToDetails(id);
    }

}
