package edu.omur.data.service;

import edu.omur.data.controller.MovieRequest;
import edu.omur.data.controller.MovieResponse;
import edu.omur.data.repository.MovieEntity;
import edu.omur.data.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Long createNewMovie(MovieRequest movieRequest) {
        MovieEntity movieEntity = Mapper.createFromMovieRequestToEntity(movieRequest);
        movieEntity = movieRepository.save(movieEntity);
        return movieEntity.getId();
    }

    public List<MovieResponse> getAllMovies() {
        return Mapper.createFromMovieEntityListToResponseList(movieRepository.findAll());
    }

    public MovieResponse getMovieById(Long id) {
        Optional<MovieEntity> requestedMovie = movieRepository.findById(id);
        if (!requestedMovie.isPresent()) {
            throw new MovieNotFoundException(String.format("Movie with id: '%s' not found!", id));
        }
        return Mapper.createFromMovieEntityToResponse(requestedMovie.get());
    }

    @Transactional
    public MovieResponse updateMovie(Long id, MovieRequest movieRequest) {
        Optional<MovieEntity> movieReadFromDB = movieRepository.findById(id);
        if (!movieReadFromDB.isPresent()) {
            throw new MovieNotFoundException(String.format("Movie with id: '%s' not found!", id));
        }
        MovieEntity movieEntity = movieReadFromDB.get();
        Mapper.createFromMovieRequestToEntity(movieEntity, movieRequest);
        return Mapper.createFromMovieEntityToResponse(movieEntity);
    }

    public void deleteMovieById(Long id) {
        movieRepository.deleteById(id);
    }
}
