package com.karinadev.literaturalibros.model;

public interface BooksMetodos {

    public  static String seleccionIdioma(String text){
        switch (text){
            case "en":
                text = "Inglés";
                break;
            case "es":
                text = "Español";
                break;
            case "fr":
                text = "Francés";
                break;
            case "de":
                text = "Alemán";
                break;
            default:
                text = "Idioma desconocido";
                break;
        }
        return text;
    }
}
