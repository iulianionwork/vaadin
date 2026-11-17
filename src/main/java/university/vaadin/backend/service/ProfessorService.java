package university.vaadin.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.vaadin.backend.dto.professor.CreateProfessorRequest;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.backend.dto.professor.UpdateProfessorRequest;
import university.vaadin.backend.mapper.ProfessorMapper;
import university.vaadin.backend.exception.NotFoundException;
import university.vaadin.backend.model.Professor;
import university.vaadin.backend.model.Role;
import university.vaadin.backend.model.UserCredentials;
import university.vaadin.backend.repository.ProfessorRepository;
import university.vaadin.backend.repository.UserRepository;
import university.vaadin.backend.service.component.EmailGenerator;

import java.util.List;

@Service
@AllArgsConstructor
public class ProfessorService{

    private final PasswordEncoder passwordEncoder;
    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;
    private final ProfessorMapper professorMapper;
    private final EmailGenerator emailGenerator;


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ProfessorResponse create(CreateProfessorRequest request){

        Professor professor = professorMapper.toEntity(request);
        professorRepository.save(professor);

        String email = emailGenerator.generate(
                professor.getFirstName(),
                professor.getLastName(),
                professor.getId(),
                "@professor.com");

        UserCredentials professorUser = new UserCredentials(
                email,
                passwordEncoder.encode(request.password()),
                Role.PROFESSOR);

        professorUser.setProfessor(professor);
        userRepository.save(professorUser);

        return professorMapper.toDto(professorUser);
    }



    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProfessorResponse> getProfessors(){

        return userRepository.findAllProfessors().stream()
                .map(professorMapper::toDto)
                .toList();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void updateProfessor(Long professorId, UpdateProfessorRequest request){
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new NotFoundException("Professor not found: " + professorId));

        professorMapper.updateEntity(request, professor);
        professorRepository.save(professor);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProfessor(Long professorId){
        UserCredentials professorUser = userRepository.findByProfessorId(professorId)
                .orElseThrow(() -> new NotFoundException("Professor not found: " + professorId));
        Professor professor = professorUser.getProfessor();


        if(!professor.getCourses().isEmpty()){
            throw new IllegalStateException("Professor still has courses!");
        }

        userRepository.delete(professorUser);
        professorRepository.delete(professor);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ProfessorResponse getProfessorById(Long professorId){
        UserCredentials professorUser = userRepository.findByProfessorId(professorId)
                .orElseThrow(() -> new NotFoundException("Professor not found: " + professorId));
        return professorMapper.toDto(professorUser);
    }
}
