package com.alura.bookmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroItem(
        @JsonAlias("results") List<DatosLibro> datosLibrosList){
}
