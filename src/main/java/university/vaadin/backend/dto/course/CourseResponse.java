package university.vaadin.backend.dto.course;

public record CourseResponse(
        Long id,
        String courseName,
        String professorName) {
}
