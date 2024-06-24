package com.alura.bookmatch.model;

import jakarta.persistence.*;

@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int fechaNacimiento;
    private int fechaDeceso;
    @ManyToOne
    private Libro libro;

    public Autor() {}

    public Autor(DatosAutor datosAutor){
        this.nombre=datosAutor.nombre();
        this.fechaNacimiento=datosAutor.fechaNacimento();
        this.fechaDeceso=datosAutor.fechaDeceso();
    }



    public int getFechaDeceso() {
        return fechaDeceso;
    }

    public void setFechaDeceso(int fechaDeceso) {
        this.fechaDeceso = fechaDeceso;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
