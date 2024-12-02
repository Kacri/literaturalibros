package com.karinadev.literaturalibros.repository;

import com.karinadev.literaturalibros.model.Authors;
import com.karinadev.literaturalibros.model.Books;
import com.karinadev.literaturalibros.model.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books,Long> {
    @Query("SELECT COUNT(b) > 0 FROM Books b WHERE b.titulo = :titulo")
    boolean existTeTitulo(@Param("titulo") String titulo);

    @Query("SELECT b FROM Books b JOIN b.authorsList a  ORDER BY b.id DESC")
    List<Books> listaDeLibros();

    @Query("SELECT b FROM Authors b  ORDER BY b.id DESC")
    List<Authors> listaDeAutores();

    @Query("SELECT b FROM Authors b WHERE b.fechaD > :fecha AND b.fechaNacimiento < :fecha")
    List<Authors> listaDeAutoresVivos(Long fecha);

    @Query("SELECT l FROM Languages l WHERE l.idioma LIKE :idioma")
    List<Languages> listarLibrosPorIdiomas(String idioma);

    @Query("SELECT b FROM Books b ")
    List<Books> listarLibros();



}
