package university.vaadin.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.vaadin.backend.dto.carnet.CarnetResponse;
import university.vaadin.backend.mapper.CarnetMapper;
import university.vaadin.backend.exception.NotFoundException;
import university.vaadin.backend.model.Carnet;
import university.vaadin.backend.model.Student;
import university.vaadin.backend.repository.CarnetRepository;
import university.vaadin.backend.repository.StudentRepository;
import university.vaadin.backend.service.component.StudentCodeGenerator;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CarnetService {

    private final CarnetRepository carnetRepository;
    private final StudentRepository studentRepository;
    private final CarnetMapper carnetMapper;
    private final UserService userService;
    private final StudentCodeGenerator studentCodeGenerator;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CarnetResponse issueCarnet(Long studentId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found: " + studentId));

        if(student.getCarnet() != null){
            throw new IllegalStateException("Student already has a carnet!");
        }
        Carnet carnet = new Carnet(student, studentCodeGenerator.generate(studentId));
        carnetRepository.save(carnet);

        return carnetMapper.toDto(carnet);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('STUDENT')")
    public Optional<CarnetResponse> myCarnet(){
        Student student = userService.getStudent();
        return carnetRepository.findCarnetByStudentId(student.getId()).map(carnetMapper::toDto);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<CarnetResponse> findCarnetByStudentId(Long studentId){
        return carnetRepository.findCarnetByStudentId(studentId)
                .map(carnetMapper::toDto);
    }
}
