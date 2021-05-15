package edu.omur.data.repository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private float rating;

    @Column(name = "IMDB_ID", unique = true)
    private String imdbId;
}