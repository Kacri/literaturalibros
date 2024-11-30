package com.karinadev.literaturalibros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBooks(@JsonAlias("title") String titulo,
                         @JsonAlias("authors") List<DatosAutor> datosAutorList,
                         @JsonAlias("languages") List<String> idiomaList,
                         @JsonAlias("download_count") Double numeroDeDescargas
                         ){

}
