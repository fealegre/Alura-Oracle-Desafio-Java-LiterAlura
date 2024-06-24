package com.alura.bookmatch.repository;

import com.alura.bookmatch.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Override
    List<Libro> findAll();
    List<Libro> findByIdiomas(String opcion);
}
