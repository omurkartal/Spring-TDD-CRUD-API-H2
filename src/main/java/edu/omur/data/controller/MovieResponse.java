package edu.omur.data.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResponse {
    private Long id;
    private String name;
    private int year;
    private float rating;
    private String imdbId;
}