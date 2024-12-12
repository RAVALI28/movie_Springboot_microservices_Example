package com.springboot.microservices.Service;

import com.springboot.microservices.Exception.DataNotFoundException;
import com.springboot.microservices.Exception.InvalidDataException;
import com.springboot.microservices.Model.Movie;
import com.springboot.microservices.Repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Transactional annotation is used to make sure that set of operation on database operated in a transactional context
//meaning that treated as a single unit either completes entirely or fails entirely
@Service
@Transactional
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    //CRUD Operations-- Create, Read, Update, delete

    //Create
    public Movie create(Movie movie){
        if(movie == null){
            throw new InvalidDataException("Invalid Movie: null");
        }

       return movieRepository.save(movie);
    }

    //Read
    public Movie read(long id){
       return movieRepository.findById(id)
               .orElseThrow(() -> new DataNotFoundException("Movie not found with id:" +id));
    }

    //Update
    public void update(Long id, Movie movie){
        if(movie == null || id == null){
            throw new InvalidDataException("Invalid movie : null");
        }

        //Check if exists
        if(movieRepository.existsById(id)) {
            Movie existedMovie = movieRepository.getReferenceById(id);
            existedMovie.setName(movie.getName());
            existedMovie.setDirector(movie.getDirector());
            existedMovie.setActors(movie.getActors());
            movieRepository.save(existedMovie);
        }
        else {
            throw new DataNotFoundException("Movie not found with id:" +id);
        }

    }

    //Delete
    public void delete(Long id){
        if (movieRepository.existsById(id)){
            movieRepository.deleteById(id);
        }
        else {
            throw new DataNotFoundException("Movie not found with id:" +id);
        }
    }
}
