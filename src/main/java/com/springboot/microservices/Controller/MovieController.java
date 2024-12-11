package com.springboot.microservices.Controller;

import com.springboot.microservices.Model.Movie;
import com.springboot.microservices.Service.MovieService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);


    @Autowired
    private MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable long id){
        Movie movie = movieService.read(id);
        log.info("Returned movie with id: {}", id);
        return ResponseEntity.ok(movie);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        Movie createdMovie = movieService.create(movie);
        log.info("Created movie with id: {}", createdMovie.getId());
        return ResponseEntity.ok(createdMovie);
    }

    @PutMapping("/{id}")
    public void updateMovie(@PathVariable long id, @RequestBody Movie movie){
        movieService.update(id, movie);
        log.info("Updated movie with id: {}", id);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable long id){
        movieService.delete(id);
        log.info("Deleted movie with id: {}", id);
    }

}
