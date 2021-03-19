package pl.kulig.testcontainers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kulig.testcontainers.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
