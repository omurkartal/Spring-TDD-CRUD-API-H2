package edu.omur.data.controller;

import edu.omur.data.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Void> createNewMovie(@Valid @RequestBody MovieRequest movieRequest, UriComponentsBuilder uriComponentsBuilder) {
        Long primaryKey = movieService.createNewMovie(movieRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("/api/movies/{id}").buildAndExpand(primaryKey);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable("id") Long id, @Valid @RequestBody MovieRequest movieRequest) {
        return ResponseEntity.ok(movieService.updateMovie(id, movieRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") Long id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.ok().build();
    }
}