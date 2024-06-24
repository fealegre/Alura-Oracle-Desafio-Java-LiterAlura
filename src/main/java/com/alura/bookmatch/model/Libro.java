package com.alura.bookmatch.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="libros")
public class Libro{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Autor> autores;
    private String idiomas;
    private Double descargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.titulo= datosLibro.titulo();
        this.descargas=datosLibro.descargas();
        this.idiomas=datosLibro.idiomas().get(0);
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        autores.forEach(a->a.setLibro(this));
        this.autores = autores;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
