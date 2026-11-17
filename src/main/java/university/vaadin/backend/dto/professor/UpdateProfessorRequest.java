package university.vaadin.backend.dto.professor;

public record UpdateProfessorRequest(
        String firstName,
        String lastName,
        String department) {
}
