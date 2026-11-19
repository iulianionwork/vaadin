package university.vaadin.ui.admin.group.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.exception.NotFoundException;
import university.vaadin.backend.service.GroupService;
import university.vaadin.ui.common.Notifications;
import university.vaadin.ui.professor.DetailsView;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class GroupPresenter {

    private final GroupService groupService;
    private DetailsView<GroupResponse> view;

    public void init(DetailsView<GroupResponse> view){
        this.view=view;

    }

    public void load(Long groupId) {
        try {
            List<StudentResponse> students = groupService.getStudentsFromGroup(groupId);
            view.show(groupService.getGroupById(groupId));
            view.showStudents(students);

        }catch (NotFoundException | AccessDeniedException e){
            Notifications.error(e.getMessage());
        }
    }

    public void onBackClicked(){
        view.navigateBack();
    }
}