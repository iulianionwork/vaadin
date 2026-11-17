package university.vaadin.backend.mapper;

import org.springframework.stereotype.Component;
import university.vaadin.backend.dto.professor.CreateProfessorRequest;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.backend.dto.professor.UpdateProfessorRequest;
import university.vaadin.backend.model.Professor;
import university.vaadin.backend.model.UserCredentials;

import java.util.Optional;

@Component
public class ProfessorMapper implements ToDtoMapper<UserCredentials, ProfessorResponse>, ToEntityMapper<CreateProfessorRequest, Professor>, UpdateMapper<UpdateProfessorRequest, Professor> {


    @Override
    public ProfessorResponse toDto(UserCredentials dto) {
        Professor professor = dto.getProfessor();
        return new ProfessorResponse(
                professor.getId(),
                dto.getEmail(),
                professor.getFirstName(),
                professor.getLastName(),
                professor.getDepartment());
    }

    @Override
    public Professor toEntity(CreateProfessorRequest dto) {
        return new Professor(dto.firstName(),  dto.lastName(), dto.department());
    }

    @Override
    public void updateEntity(UpdateProfessorRequest dto, Professor entity) {
        Optional.ofNullable(dto.firstName())
                .ifPresent(entity::setFirstName);

        Optional.ofNullable(dto.lastName())
                .ifPresent(entity::setLastName);

        Optional.ofNullable(dto.department())
                .ifPresent(entity::setDepartment);
    }
}
