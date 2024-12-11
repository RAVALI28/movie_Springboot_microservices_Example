package com.springboot.microservices.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "MovieName")
    private String name;

    @Column(name = "DirectorName")
    private String director;

    //ElementCollection annotation is used to map the collection of data(String, integers)
    // to the seperate entity that associated with in an entity
    @Column(name = "Actors")
    @ElementCollection
    private List<String> actors = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }
}
