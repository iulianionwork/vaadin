package university.vaadin.backend.mapper;

import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.course.CourseResponse;
import university.vaadin.backend.model.Course;
import university.vaadin.backend.model.Professor;

@Component
public class CourseMapper implements ToDtoMapper<Course, CourseResponse>  {
    @Override
    public CourseResponse toDto(Course entity) {
        Professor professor = entity.getProfessor();
        String professorName = professor.getFirstName() + " " + professor.getLastName();
        return new CourseResponse(
                entity.getId(),
                entity.getCourseName(),
                professorName);
    }

}
