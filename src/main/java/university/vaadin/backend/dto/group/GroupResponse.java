package university.vaadin.backend.dto.group;

public record GroupResponse(
        Long id,
        String groupName,
        int studentCount) {
}
