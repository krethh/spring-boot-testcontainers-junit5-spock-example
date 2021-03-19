package pl.kulig.testcontainers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kulig.testcontainers.entities.Child;

@Repository
public interface ChildRepository extends JpaRepository<Child, Integer> {

}
