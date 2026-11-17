package university.vaadin.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import university.vaadin.backend.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
