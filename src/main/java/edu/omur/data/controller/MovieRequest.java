package edu.omur.data.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class MovieRequest {
    @NotEmpty
    private String name;

    private int year;

    private float rating;

    @NotEmpty
    @Size(max = 10)
    private String imdbId;
}
