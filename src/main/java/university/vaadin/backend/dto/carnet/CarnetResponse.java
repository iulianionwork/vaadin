package university.vaadin.backend.dto.carnet;

import java.time.LocalDate;

public record CarnetResponse(
        Long id,
        String carnetNumber,
        LocalDate issueDate) {
}
