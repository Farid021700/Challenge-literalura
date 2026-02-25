package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        String name,
        @JsonAlias("birth_year")
        Integer birthYear,
        @JsonAlias("death_year")
        Integer deathYear
) {}