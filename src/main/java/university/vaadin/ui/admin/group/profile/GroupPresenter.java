package university.vaadin.ui.admin.group.profile;

import lombok.RequiredArgsConstructor;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.service.GroupService;
import university.vaadin.ui.common.annotation.Presenter;
import university.vaadin.ui.common.presenter.DetailsPresenter;

import java.util.List;

@Presenter
@RequiredArgsConstructor
public class GroupPresenter extends DetailsPresenter<GroupResponse> {

    private final GroupService groupService;

    @Override
    protected GroupResponse findById(Long id) {
        return groupService.getGroupById(id);
    }

    @Override
    protected List<StudentResponse> findStudents(Long id) {
        return groupService.getStudentsFromGroup(id);
    }
}