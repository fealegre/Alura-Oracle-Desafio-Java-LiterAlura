package com.alura.bookmatch.repository;

import com.alura.bookmatch.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Override
    List<Autor> findAll();
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento>= :fnac AND a.fechaNacimiento<= :fnac+50")
    List<Autor> listaAutoresPoFechaNacimiento(Integer fnac);
}
