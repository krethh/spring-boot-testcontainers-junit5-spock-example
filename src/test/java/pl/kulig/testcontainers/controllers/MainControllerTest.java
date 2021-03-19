package pl.kulig.testcontainers.controllers;

import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kulig.testcontainers.entities.Child;
import pl.kulig.testcontainers.entities.Person;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public class MainControllerTest extends PostgresTestBase {

    @Autowired
    MainController mainController;

    @Test
    @Transactional
    @Order(1)
    public void testGetAllPersons() {
        List<Person> allPersons = mainController.getAllPersons();

        Assert.assertEquals(allPersons.size(), 2);
    }

    @Test
    @Transactional
    @Order(2)
    public void testGetAllChildren() {
        List<Child> children = mainController.getAllChildren();

        Assert.assertEquals(children.size(), 2);
    }

    @Test
    @Transactional
    @Order(3)
    public void testGetOnePerson() {
        Person person = mainController.getById(1);

        Assert.assertEquals(person.getChildren().size(), 1);
    }

    @Test
    @Transactional
    @Order(4)
    public void addPersonAndCheckTheNumber() {
        final var personToAdd = new Person();
        personToAdd.setName("person3");
        personToAdd.setDateOfBirth(LocalDate.of(1970, 1, 1));

        mainController.addPerson(personToAdd);

        final var persons = mainController.getAllPersons();
        final var justAddedPerson = mainController.getById(3);

        Assert.assertEquals(3, persons.size());
        Assert.assertEquals("person3", justAddedPerson.getName());
    }

    @Test
    @Transactional
    @Order(5)
    public void deletePreviouslyAddedPerson() {
        final var personToAdd = new Person();
        personToAdd.setName("person3");
        personToAdd.setDateOfBirth(LocalDate.of(1970, 1, 1));

        final var addedId = mainController.addPerson(personToAdd);
        mainController.deleteById(addedId);

        final var allPersons = mainController.getAllPersons();
        Assert.assertEquals(2, allPersons.size());
    }
}
