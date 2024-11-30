package com.karinadev.literaturalibros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors")
public class Authors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
   @Column(unique = true)
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaD;

//    @OneToMany(mappedBy = "authors",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    private List<Books> booksList;

    @ManyToOne
    private Books books;

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public Authors(){}

    public Authors(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaD = datosAutor.fechaD();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authors authors = (Authors) o;
        return Objects.equals(nombre, authors.nombre); // Comparar solo por nombre
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre); // Generar hash basado en nombre
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaD() {
        return fechaD;
    }

    public void setFechaD(Integer fechaD) {
        this.fechaD = fechaD;
    }

@Override
    public String toString(){
        return  "\n ----------------- AUTORES Y SUS LIBROS -------------"+
                "\n Nombre: "+nombre +
                "\n Fecha de Nacimiento: "+fechaNacimiento+
                "\n Fecha de Fallecimiento: "+fechaD+
                "\n Libros: "+books.getTitulo()+
                "\n ";
}



}
