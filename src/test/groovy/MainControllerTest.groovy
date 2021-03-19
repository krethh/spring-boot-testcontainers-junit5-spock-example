import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import pl.kulig.testcontainers.controllers.MainController
import pl.kulig.testcontainers.entities.Person

class MainControllerTest extends PostgresTestBaseSpock {

    @Autowired
    MainController mainController;

    @Test
    def "Persons are fetched correctly" () {
        when:
        def response = get("persons", List.class)

        then:
        response.body.size() == 2
    }

    @Test
    def "Children are fetched correctly" () {
        when:
        def response = get("children", List.class)

        then:
        response.body.size() == 2
    }

    @Test
    def "Person is fetched correctly by id" () {
        when:
        def response = get("persons/1", Person.class)

        then:
        response.body.name == "mama"
    }

    @Test
    def "Person is created and retrieved correctly" () {
        given:
        def personToAdd = new Person()
        personToAdd.name = 'person3'

        when:
        def addedPersonId = put("persons", personToAdd).body
        def addedPerson = get("persons/$addedPersonId", Person.class)

        then:
        addedPerson.body.name == 'person3'
    }
}
