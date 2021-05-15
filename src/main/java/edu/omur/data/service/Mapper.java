package edu.omur.data.service;

import edu.omur.data.controller.MovieRequest;
import edu.omur.data.controller.MovieResponse;
import edu.omur.data.repository.MovieEntity;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static MovieEntity createFromMovieRequestToEntity(MovieRequest movieRequest) {
        return createFromMovieRequestToEntity(new MovieEntity(), movieRequest);
    }

    public static MovieEntity createFromMovieRequestToEntity(MovieEntity movieEntity, MovieRequest movieRequest) {
        movieEntity.setName(movieRequest.getName());
        movieEntity.setYear(movieRequest.getYear());
        movieEntity.setRating(movieRequest.getRating());
        movieEntity.setImdbId(movieRequest.getImdbId());
        return movieEntity;
    }

    public static List<MovieResponse> createFromMovieEntityListToResponseList(List<MovieEntity> movieEntityList) {
        return movieEntityList.stream()
                .map(Mapper::createFromMovieEntityToResponse)
                .collect(Collectors.toList());
    }

    public static MovieResponse createFromMovieEntityToResponse(MovieEntity movieEntity) {
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setId(movieEntity.getId());
        movieResponse.setName(movieEntity.getName());
        movieResponse.setYear(movieEntity.getYear());
        movieResponse.setRating(movieEntity.getRating());
        movieResponse.setImdbId(movieEntity.getImdbId());
        return movieResponse;
    }
}
