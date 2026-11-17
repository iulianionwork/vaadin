package university.vaadin.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import university.vaadin.backend.model.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("""
    SELECT g FROM Group g
    LEFT JOIN FETCH g.students
    """)
    @Override
  List<Group> findAll();
}
