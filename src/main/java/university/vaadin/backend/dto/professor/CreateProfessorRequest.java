package university.vaadin.backend.dto.professor;

import jakarta.validation.constraints.NotBlank;

public record CreateProfessorRequest(
        @NotBlank(message = "First Name can't be null or blank!") String firstName,
        @NotBlank(message = "Last Name can't be null or blank!") String lastName,
        @NotBlank(message = "Password can't be null or blank!") String password,
        @NotBlank(message = "Department can't be null or blank!") String department) {

}
