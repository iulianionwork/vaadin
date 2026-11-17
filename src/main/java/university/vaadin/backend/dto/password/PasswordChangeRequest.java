package university.vaadin.backend.dto.password;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequest(
        @NotBlank String oldPassword,
        @NotBlank String newPassword) {
}
