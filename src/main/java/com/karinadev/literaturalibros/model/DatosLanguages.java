package com.karinadev.literaturalibros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLanguages(
        @JsonAlias("languages") String idioma
) {

}
