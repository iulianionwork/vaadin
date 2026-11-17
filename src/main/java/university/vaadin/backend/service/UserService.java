package university.vaadin.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.vaadin.backend.dto.professor.ProfessorResponse;
import university.vaadin.backend.dto.student.StudentResponse;
import university.vaadin.backend.mapper.ProfessorMapper;
import university.vaadin.backend.mapper.StudentMapper;
import university.vaadin.backend.exception.NotFoundException;
import university.vaadin.backend.model.Professor;
import university.vaadin.backend.model.Student;
import university.vaadin.backend.model.UserCredentials;
import university.vaadin.backend.repository.UserRepository;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final ProfessorMapper professorMapper;


    @Override
    public UserDetails loadUserByUsername(String email) {

        UserCredentials user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));

            return User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();

    }


    protected UserCredentials get(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email: " + email + " not found"));
    }


    protected Student getStudent(){
        UserCredentials user = get();
        if(user.getStudent() == null){
            throw new AccessDeniedException("User is not logged as a student");
        }

        return user.getStudent();
    }


    protected Professor getProfessor(){
        UserCredentials user = get();
        if(user.getProfessor() == null){
            throw new AccessDeniedException("User is not logged as a professor");
        }

        return user.getProfessor();
    }

    @Transactional(readOnly = true)
    public StudentResponse getCurrentStudent(){
        return studentMapper.toDto(get());
    }

    @Transactional(readOnly = true)
    public ProfessorResponse getCurrentProfessor(){
        return professorMapper.toDto(get());
    }







}
