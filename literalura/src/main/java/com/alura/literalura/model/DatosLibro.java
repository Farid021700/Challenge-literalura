package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        String title,
        List<DatosAutor> authors,
        List<String> languages,
        @JsonAlias("download_count")
        Double downloadCount
) {}