package university.vaadin.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.vaadin.backend.dto.group.GroupRequest;
import university.vaadin.backend.dto.group.GroupResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.mapper.GroupMapper;
import university.vaadin.backend.mapper.StudentMapper;
import university.vaadin.backend.exception.NotFoundException;
import university.vaadin.backend.model.Group;
import university.vaadin.backend.model.Student;
import university.vaadin.backend.repository.GroupRepository;
import university.vaadin.backend.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupMapper groupMapper;
    private final StudentMapper studentMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public GroupResponse createGroup(GroupRequest dto) {
        Group group = groupRepository.save(groupMapper.toEntity(dto));
        return groupMapper.toDto(group);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<GroupResponse> getGroups() {
        return  groupRepository.findAll().stream()
                .map(groupMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<StudentResponse> getStudentsFromGroup(Long groupId) {

        if(!groupRepository.existsById(groupId)) {
            throw new NotFoundException("Group not found: " + groupId);
        }
        return userRepository.getStudentsByGroupId(groupId).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found: " + groupId));

        for(Student student : group.getStudents()){
            student.setGroup(null);
        }
        groupRepository.delete(group);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public GroupResponse getGroupById(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found: " + groupId));
        return groupMapper.toDto(group);
    }



}
