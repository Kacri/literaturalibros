package com.karinadev.literaturalibros.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json,Class<T> clase);
}
