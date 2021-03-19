package pl.kulig.testcontainers.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.kulig.testcontainers.entities.Child;
import pl.kulig.testcontainers.entities.Person;
import pl.kulig.testcontainers.exceptions.EntityNotFoundException;
import pl.kulig.testcontainers.repositories.ChildRepository;
import pl.kulig.testcontainers.repositories.PersonRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final PersonRepository personRepository;
    private final ChildRepository childRepository;

    @GetMapping("persons")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("persons/{id}")
    public Person getById(@PathVariable Integer id) {
        return personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("children")
    public List<Child> getAllChildren() {
        return childRepository.findAll();
    }

    @PutMapping("persons")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer addPerson(@RequestBody Person person) {
        return personRepository.save(person).getId();
    }

    @DeleteMapping("persons/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        personRepository.deleteById(id);
    }

}
