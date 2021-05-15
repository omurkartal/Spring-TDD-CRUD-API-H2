package edu.omur.data;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class MovieControllerTest {
    @LocalServerPort
    int randomPort;

    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        this.testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void deletingKnownEntityShouldReturn404AfterDeletion() {
        String baseUrl = String.format("http://localhost:%d/api/movies/1", randomPort);

        ResponseEntity<JsonNode> firstResult = this.testRestTemplate.getForEntity(baseUrl, JsonNode.class);
        assertThat(firstResult.getStatusCode(), is(HttpStatus.OK));

        this.testRestTemplate.delete(baseUrl);

        ResponseEntity<JsonNode> secondResult = this.testRestTemplate.getForEntity(baseUrl, JsonNode.class);
        assertThat(secondResult.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }
}