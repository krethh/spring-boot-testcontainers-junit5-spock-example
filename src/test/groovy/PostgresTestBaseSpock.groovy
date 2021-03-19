import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import pl.kulig.testcontainers.TestcontainersLabApplication
import spock.lang.Shared
import spock.lang.Specification

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestcontainersLabApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
abstract class PostgresTestBaseSpock extends Specification {

    private baseUrl = "http://localhost:8080"

    @Shared
    RestTemplate restTemplate = new RestTemplate()

    @Shared
    JdbcDatabaseContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withInitScript("db-scripts/00-POPULATE-DB.sql")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withDatabaseName("password");

    def setupSpec() {
        if (!postgreSQLContainer.isRunning()) {
            postgreSQLContainer.start()
        }
        ['spring.datasource.url'     : postgreSQLContainer.getJdbcUrl(),
         'spring.datasource.username': postgreSQLContainer.getUsername(),
         'spring.datasource.password': postgreSQLContainer.getPassword()
        ].each { k, v ->
            System.setProperty(k, v)
        }
    }

    def cleanupSpec() {
        if (postgreSQLContainer.isRunning()) {
            postgreSQLContainer.stop()
        }
    }

    def get(url, clazz) {
        return restTemplate.getForEntity("$baseUrl/$url", clazz)
    }

    def delete(url) {
        return restTemplate.delete("$baseUrl/$url")
    }

    def put(url, data, responseClass = String.class) {
        return restTemplate.exchange("$baseUrl/$url", HttpMethod.PUT, new HttpEntity<Object>(data), responseClass)
    }

}