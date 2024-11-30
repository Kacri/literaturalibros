package com.karinadev.literaturalibros.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Double numeroDeDescargas;

    @OneToMany(mappedBy = "books",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Authors> authorsList;

    public List<Authors> getAuthorsList() {
        return authorsList;
    }


    public void setAuthorsList(List<Authors> authorsList) {
        authorsList.forEach(k-> k.setBooks(this));
        this.authorsList = authorsList;
    }

    @OneToMany(mappedBy = "books",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Languages> languagesList;

    public List<Languages> getLanguagesList() {
        return languagesList;
    }

    public void setLanguagesList(List<Languages> languagesList) {
        languagesList.forEach(k -> k.setBooks(this));
        this.languagesList = languagesList;
    }



public Books(
){
}

    public Books(DatosBooks datosBooks){
        this.titulo = datosBooks.titulo();
        this.numeroDeDescargas = datosBooks.numeroDeDescargas();

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }



    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

@Override
    public String toString(){

    int i = 0;
    return
            "\n ----------------- LIBRO -------------"+
                        "\n Titulo: " + titulo +
                        "\n Autor: '"+authorsList.get(i).getNombre() + '\''+
                        "\n Idioma: '" + languagesList.get(i).getDescripcion() + '\''+
                        "\n Numero de descargas: '"+numeroDeDescargas + '\''+
                                "\n-------------------------------------";

}




}
