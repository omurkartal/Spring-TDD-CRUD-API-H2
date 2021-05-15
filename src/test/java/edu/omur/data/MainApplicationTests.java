package edu.omur.data;

import edu.omur.data.controller.MovieController;
import edu.omur.data.repository.MovieRepository;
import edu.omur.data.service.MovieService;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.notNullValue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MainApplicationTests {
    @Autowired
    MovieController movieController;

    @Autowired
    MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void contextLoads() {
        MatcherAssert.assertThat(movieController, notNullValue());
        MatcherAssert.assertThat(movieService, notNullValue());
        MatcherAssert.assertThat(movieRepository, notNullValue());
    }
}