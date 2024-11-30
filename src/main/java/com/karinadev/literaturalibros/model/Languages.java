package com.karinadev.literaturalibros.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "languages")
public class Languages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idioma;
    private String descripcion;
    public Languages(){}
    public Languages(String datosLanguages){
        this.idioma = datosLanguages;
        this.descripcion = BooksMetodos.seleccionIdioma(datosLanguages);
    }

@ManyToOne
private Books books;

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

@Override
    public String toString(){
        int i = 0;
        return "\n --------- LIBRO -------"+
                "\n Titulo: " + books.getTitulo() +
                "\n Autor: '"+books.getAuthorsList().get(0).getNombre() + '\''+
                "\n Idioma: '" + books.getLanguagesList().get(i).getDescripcion() + '\''+
                "\n Numero de descargas: '"+books.getNumeroDeDescargas() + '\''+
                "\n-------------------------------------";

}

}
