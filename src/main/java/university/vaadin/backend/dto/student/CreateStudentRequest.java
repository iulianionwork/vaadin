package university.vaadin.backend.dto.student;

import jakarta.validation.constraints.NotBlank;

public record CreateStudentRequest(
        @NotBlank(message = "First Name can't be null or blank!") String firstName,
        @NotBlank(message = "Last Name can't be null or blank!") String lastName,
        @NotBlank(message = "Password can't be null or blank!") String password) {

}
