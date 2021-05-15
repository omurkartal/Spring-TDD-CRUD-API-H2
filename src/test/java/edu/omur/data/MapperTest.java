package edu.omur.data;

import edu.omur.data.controller.MovieRequest;
import edu.omur.data.controller.MovieResponse;
import edu.omur.data.repository.MovieEntity;
import edu.omur.data.service.Mapper;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;

public class MapperTest {

    @Test
    public void testCreateFromMovieRequestToEntity() {
        MovieRequest movieRequest = createMovieRequest();
        MovieEntity movieEntity = Mapper.createFromMovieRequestToEntity(movieRequest);
        MatcherAssert.assertThat(movieEntity.getName(), is(movieRequest.getName()));
        MatcherAssert.assertThat(movieEntity.getYear(), is(movieRequest.getYear()));
        MatcherAssert.assertThat(movieEntity.getRating(), is(movieRequest.getRating()));
        MatcherAssert.assertThat(movieEntity.getImdbId(), is(movieRequest.getImdbId()));
    }

    @Test
    public void testCreateFromMovieEntityListToResponseList() {
        List<MovieEntity> movieEntityList = createMovieEntityList();
        List<MovieResponse> responseList = Mapper.createFromMovieEntityListToResponseList(movieEntityList);
        for (int i = 0; i < movieEntityList.size(); i++) {
            MovieEntity movieEntity = movieEntityList.get(i);
            MovieResponse movieResponse = responseList.get(i);
            MatcherAssert.assertThat(movieResponse.getId(), is(movieEntity.getId()));
            MatcherAssert.assertThat(movieResponse.getName(), is(movieEntity.getName()));
            MatcherAssert.assertThat(movieResponse.getYear(), is(movieEntity.getYear()));
            MatcherAssert.assertThat(movieResponse.getRating(), is(movieEntity.getRating()));
            MatcherAssert.assertThat(movieResponse.getImdbId(), is(movieEntity.getImdbId()));
        }
    }

    @Test
    public void testCreateFromMovieEntityToResponse() {
        MovieEntity movieEntity = createMovieEntity();
        MovieResponse movieResponse = Mapper.createFromMovieEntityToResponse(movieEntity);
        MatcherAssert.assertThat(movieResponse.getId(), is(movieEntity.getId()));
        MatcherAssert.assertThat(movieResponse.getName(), is(movieEntity.getName()));
        MatcherAssert.assertThat(movieResponse.getYear(), is(movieEntity.getYear()));
        MatcherAssert.assertThat(movieResponse.getRating(), is(movieEntity.getRating()));
        MatcherAssert.assertThat(movieResponse.getImdbId(), is(movieEntity.getImdbId()));
    }

    private MovieRequest createMovieRequest() {
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setName("Movie Name");
        movieRequest.setYear(2014);
        movieRequest.setRating(8.2F);
        movieRequest.setImdbId("imdb-id");
        return movieRequest;
    }

    private MovieEntity createMovieEntity() {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(1L);
        movieEntity.setName("Movie Name");
        movieEntity.setYear(2014);
        movieEntity.setRating(8.2F);
        movieEntity.setImdbId("imdb-id");
        return movieEntity;
    }

    private List<MovieEntity> createMovieEntityList() {
        List<MovieEntity> list = new ArrayList<>();
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(1L);
        movieEntity.setName("Movie Name");
        movieEntity.setYear(2011);
        movieEntity.setRating(8.1F);
        movieEntity.setImdbId("imdb-id-1");
        list.add(movieEntity);

        movieEntity = new MovieEntity();
        movieEntity.setId(2L);
        movieEntity.setName("Movie Name-2");
        movieEntity.setYear(2012);
        movieEntity.setRating(8.2F);
        movieEntity.setImdbId("imdb-id-2");
        list.add(movieEntity);

        movieEntity = new MovieEntity();
        movieEntity.setId(3L);
        movieEntity.setName("Movie Name-3");
        movieEntity.setYear(2013);
        movieEntity.setRating(8.3F);
        movieEntity.setImdbId("imdb-id-3");
        list.add(movieEntity);

        return list;
    }
}
