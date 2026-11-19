package university.vaadin.backend.dto.professor;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfessorRequest(
        @NotBlank(message = "First Name can't be null or blank!") String firstName,
        @NotBlank(message = "Last Name can't be null or blank!") String lastName,
        @NotBlank(message = "Department can't be null or blank!") String department) {
}
