package com.springboot.microservices.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.microservices.Model.Movie;
import com.springboot.microservices.Repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerIntTest {

    @Autowired
    MockMvc mockMvc;

    //ObjectMapper is the utility class from Jackson Library used to Serialize(Object to JSON)
    // and de-serialize(JSON to Object)
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MovieRepository movieRepository;

    @BeforeEach
    void cleanUp(){
        movieRepository.deleteAllInBatch();
    }


    @Test
    void givenMovie_whenCreateMovie_thenReturnMovie() throws Exception {

        //Given
            Movie movie = new Movie();
            movie.setName("RRR");
            movie.setDirector("Rajamouli");
            movie.setActors(List.of("NTR", "RAM", "Alia"));

        //When create movie
       var response = mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

       //Then verify saved movie
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));

    }


    @Test
    void givenMovieId_whenSearchMovie_thenReturnMovie() throws Exception{

        //Given
        Movie movie = new Movie();
        movie.setName("RRR");
        movie.setDirector("Rajamouli");
        movie.setActors(List.of("NTR", "RAM", "Alia"));

        Movie savedMovie = movieRepository.save(movie);

        //when
        var response = mockMvc.perform(get("/movies/" +savedMovie.getId()));


        //Then verify saved movie
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int)savedMovie.getId())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));
    }


    @Test
    void givenSavedMovie_whenUpdateMovie_thenMovieUpdatedInDb() throws Exception {
        //Given
        Movie movie = new Movie();
        movie.setName("RRR");
        movie.setDirector("Rajamouli");
        movie.setActors(List.of("NTR", "RAM", "Alia"));

        Movie savedMovie = movieRepository.save(movie);
        Long id = savedMovie.getId();

        //Update movie
        movie.setActors(List.of("NTR", "RAM", "Alia", "AjayDevgan"));

        var response = mockMvc.perform(put("/movies/" +id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        //Then Verify Updated movie
        response.andDo(print())
                .andExpect(status().isOk());

        var fetchedresponse = mockMvc.perform(get("/movies/" +id));

        fetchedresponse.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));
    }


    @Test
    void givenMovie_whenDeleteMovie_thenMovieDeletedInDb() throws Exception{

        //Given
        Movie movie = new Movie();
        movie.setName("RRR");
        movie.setDirector("Rajamouli");
        movie.setActors(List.of("NTR", "RAM", "Alia"));

        Movie savedMovie = movieRepository.save(movie);
        Long id = savedMovie.getId();

        //Then Delete
        var response = mockMvc.perform(delete("/movies/" +id))
                .andDo(print())
                .andExpect(status().isOk());


       assertFalse(movieRepository.findById(id).isPresent());
    }

}