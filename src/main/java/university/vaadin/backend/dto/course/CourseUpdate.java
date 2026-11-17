package university.vaadin.backend.dto.course;

import jakarta.validation.constraints.NotNull;

public record CourseUpdate(
        @NotNull Long professorId) {
}
