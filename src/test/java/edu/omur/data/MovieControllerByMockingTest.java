package edu.omur.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.omur.data.controller.MovieController;
import edu.omur.data.controller.MovieRequest;
import edu.omur.data.controller.MovieResponse;
import edu.omur.data.service.MovieNotFoundException;
import edu.omur.data.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@ExtendWith(SpringExtension.class)
public class MovieControllerByMockingTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieService movieService;

    @Captor
    private ArgumentCaptor<MovieRequest> movieRequestArgumentCaptor;

    @Test
    public void testMovieCreation() throws Exception {
        MovieRequest movieRequest = createMovieRequest();
        when(movieService.createNewMovie(movieRequestArgumentCaptor.capture())).thenReturn(1L);

        this.mockMvc
                .perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/movies/1"));

        assertThat(movieRequestArgumentCaptor.getValue().getName(), is("Movie Name"));
        assertThat(movieRequestArgumentCaptor.getValue().getYear(), is(2014));
        assertThat(movieRequestArgumentCaptor.getValue().getRating(), is(8.2F));
        assertThat(movieRequestArgumentCaptor.getValue().getImdbId(), is("imdb-id"));
    }

    @Test
    public void testListAllMovies() throws Exception {
        List<MovieResponse> list = new ArrayList<>();
        list.add(createMovieResponse(1L, "movie-name-1", 2011, 7.1F, "imdb-id-1"));
        list.add(createMovieResponse(2L, "movie-name-2", 2012, 7.2F, "imdb-id-2"));
        when(movieService.getAllMovies()).thenReturn(list);

        this.mockMvc
                .perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("movie-name-1")))
                .andExpect(jsonPath("$[0].year", is(2011)))
                .andExpect(jsonPath("$[0].rating", is(7.1)))
                .andExpect(jsonPath("$[0].imdbId", is("imdb-id-1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("movie-name-2")))
                .andExpect(jsonPath("$[1].year", is(2012)))
                .andExpect(jsonPath("$[1].rating", is(7.2)))
                .andExpect(jsonPath("$[1].imdbId", is("imdb-id-2")));
    }

    @Test
    public void testGetMovieById() throws Exception {
        MovieResponse movieResponse = createMovieResponse(1L, "movie-name-1", 2011, 7.1F, "imdb-id-1");
        when(movieService.getMovieById(1L)).thenReturn(movieResponse);

        this.mockMvc
                .perform(get("/api/movies/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("movie-name-1")))
                .andExpect(jsonPath("$.year", is(2011)))
                .andExpect(jsonPath("$.rating", is(7.1)))
                .andExpect(jsonPath("$.imdbId", is("imdb-id-1")));
    }

    @Test
    public void testGetMovieWithUnknownId() throws Exception {
        when(movieService.getMovieById(123L)).thenThrow(new MovieNotFoundException("Movie with id '123' not found"));
        this.mockMvc
                .perform(get("/api/movies/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateMovie() throws Exception {
        MovieRequest movieRequest = createMovieRequest();
        MovieResponse movieResponse = createMovieResponse(1L, movieRequest);
        when(movieService.updateMovie(eq(1L), movieRequestArgumentCaptor.capture())).thenReturn(movieResponse);

        this.mockMvc
                .perform(put("/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Movie Name")))
                .andExpect(jsonPath("$.year", is(2014)))
                .andExpect(jsonPath("$.rating", is(8.2)))
                .andExpect(jsonPath("$.imdbId", is("imdb-id")));

        assertThat(movieRequestArgumentCaptor.getValue().getName(), is("Movie Name"));
        assertThat(movieRequestArgumentCaptor.getValue().getYear(), is(2014));
        assertThat(movieRequestArgumentCaptor.getValue().getRating(), is(8.2F));
        assertThat(movieRequestArgumentCaptor.getValue().getImdbId(), is("imdb-id"));
    }

    @Test
    public void testMovieUpdateWithUnknownId() throws Exception {
        MovieRequest movieRequest = createMovieRequest();
        when(movieService.updateMovie(eq(12345L), movieRequestArgumentCaptor.capture()))
                .thenThrow(new MovieNotFoundException("The movie with id '12345' was not found"));

        this.mockMvc
                .perform(put("/api/movies/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isNotFound());
    }

    private MovieRequest createMovieRequest() {
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setName("Movie Name");
        movieRequest.setYear(2014);
        movieRequest.setRating(8.2F);
        movieRequest.setImdbId("imdb-id");
        return movieRequest;
    }

    private MovieResponse createMovieResponse(Long id, MovieRequest movieRequest) {
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setId(id);
        movieResponse.setName(movieRequest.getName());
        movieResponse.setYear(movieRequest.getYear());
        movieResponse.setRating(movieRequest.getRating());
        movieResponse.setImdbId(movieRequest.getImdbId());
        return movieResponse;
    }

    private MovieResponse createMovieResponse(Long id, String name, int year, float rating, String imdbId) {
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setId(id);
        movieResponse.setName(name);
        movieResponse.setYear(year);
        movieResponse.setRating(rating);
        movieResponse.setImdbId(imdbId);
        return movieResponse;
    }
}