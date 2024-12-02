package com.karinadev.literaturalibros.principal;

import com.karinadev.literaturalibros.model.*;
import com.karinadev.literaturalibros.repository.AuthorsRepository;
import com.karinadev.literaturalibros.repository.BooksRepository;
import com.karinadev.literaturalibros.service.ConsumoApi;
import com.karinadev.literaturalibros.service.ConvierteDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";

    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private BooksRepository booksRepository;
    private AuthorsRepository authorsRepository;


    public Principal(BooksRepository booksRepository){
        this.booksRepository = booksRepository;
    }


    public void muestraMenu(){
        var opcion = -1;

        while (opcion != 0 ){
            var menu = """
                    -----------------------------------
                    Escribir opcion que desee realizar
                    -----------------------------------
                    1 - Buscar Libro por Titulo
                    2 - Listar Libros Registrados
                    3 - Listar Autores Registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idiomas
                    6 - Reporte Estadisticos
                    7 - Top 10 libros mas Descargados
                    8 - Buscar Libros por Autor
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarBookWeb();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresLive();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    datosEstadisticos();
                    break;
                case 7:
                    top10librosMasDescargados();
                    break;
                case 8:
                    buscarAutororApi();
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    private  Optional<DatosBooks> getDatosResult() {
        System.out.println("Por favor escribir el Titulo del libro que desea Buscar: ");
        var tituloBooks = teclado.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloBooks.replace(" ","+"));
        Datos result = convierteDatos.obtenerDatos(json, Datos.class);

        Optional<DatosBooks> libroBuscado = result.datosLibrosList().stream()
                        .filter(l -> l.titulo().toUpperCase().contains(tituloBooks.toUpperCase()))
                                .findFirst();

            if (libroBuscado.isPresent()){
//                System.out.println("------------------------LIBRO-----------------");
//                System.out.println("Titulo: " + libroBuscado.get().titulo());
//                System.out.println("Autor: " + libroBuscado.get().datosAutorList().stream().map(DatosAutor::nombre).filter(nombre -> nombre != null).collect(Collectors.joining(", ")));
//                System.out.println("Idioma: " + libroBuscado.get().idiomaList().stream().collect(Collectors.joining(", ")));
//
//                System.out.println("Numero de Descargas: " + libroBuscado.get().numeroDeDescargas());
//                System.out.println("------------------------------------------------");
            }else {
                System.out.println("------------------------------------------------------------------");
                System.out.println("No existe Libro");
                System.out.println("------------------------------------------------------------------");
            }
            return libroBuscado;
    }



    private void buscarBookWeb(){
        Optional<DatosBooks> datosBooksOptional = getDatosResult();
        //GUARDANDO
        try{
            if (datosBooksOptional.isPresent()){
                DatosBooks datosBooks = datosBooksOptional.get();
                Books books = new Books(datosBooks);
                if (!booksRepository.existTeTitulo(books.getTitulo())){
                    try {
                        booksRepository.save(books);
                        System.out.println("------------------------------------------------------------------");
                        System.out.println("------------------------LIBRO-----------------");
                        System.out.println("Titulo: "+ books.getTitulo());
                        System.out.println("Numero de Descargas: " + books.getNumeroDeDescargas());
                    }catch (DataIntegrityViolationException ex){
                        System.out.println("Error inesperado al guardar el libro: " +ex.getMessage());
                    }
                }else {
                    System.out.println("El libro con titulo: '"+books.getTitulo() +"' ya existe." );
                }
                try {
                    List<Authors> authors = datosBooksOptional.stream()
                            .flatMap(d -> d.datosAutorList().stream()
                                    .map(a -> new Authors(a))).collect(Collectors.toList());
                    books.setAuthorsList( authors);
                    int i = 0;
                    System.out.println("Autor: " + authors.get(i).getNombre());
                }catch (DataIntegrityViolationException ex){
                    System.out.println("El Author existe");
                }

                List<Languages> languages = datosBooksOptional.stream()
                        .flatMap(d -> d.idiomaList().stream())
                        .map(a -> new Languages(a))
                        .collect(Collectors.toList());
                books.setLanguagesList(languages);
                int i = 0;
                System.out.println("Idioma: "+languages.get(i).getDescripcion());
                System.out.println("------------------------------------------------------------------");
                booksRepository.save(books);

                System.out.println("------------------------------------------------------------------");
                System.out.println("Se registro correctamente");
                System.out.println("------------------------------------------------------------------");

             }
        }catch (DataIntegrityViolationException ex){
            System.out.println("Error en Registrar Datos. ");
        }
    }

    private void listarLibrosRegistrados() {
        List<Books> listaLibros = booksRepository.listaDeLibros();
        System.out.println("----------Lista de Libros-----------------------------");
        System.out.println(listaLibros);
    }

    private void listarAutoresRegistrados(){
        List<Authors> listaAutores = booksRepository.listaDeAutores();
        System.out.println(listaAutores);
    }

    private void listarAutoresLive(){
        System.out.println("Ingresar año vivo del Autor:");
        var anio = teclado.nextLong();
        List<Authors> listarAutoresLive = booksRepository.listaDeAutoresVivos(anio);
        System.out.println(listarAutoresLive);
    }

private void listarLibrosPorIdioma(){
    System.out.println("Ingrese el Idioma que desea buscar: ");
    System.out.println("""
            es - Español
            en - Ingles
            fr - Frances
            pt - portugues
            """);
    var idioma = teclado.nextLine();
    List<Languages> languagesList = booksRepository.listarLibrosPorIdiomas(idioma);
    System.out.println(languagesList);
}

private void datosEstadisticos(){
        List<Books> booksList = booksRepository.listarLibros();
        DoubleSummaryStatistics est = booksList.stream().filter(t -> t.getNumeroDeDescargas()>9)
//                .peek(t -> System.out.println("Libro: "+t.getTitulo()))
                .mapToDouble(t -> t.getNumeroDeDescargas())
                .summaryStatistics();
//Busca el libro
        String libroMasDescargado = booksList.stream()
                        .filter(t -> t.getNumeroDeDescargas() == est.getMax())
                                .map(Books::getTitulo)
                                        .findFirst()
                                                .orElse("No se encontro ninguno");
    String libroConMenosDescargas = booksList.stream()
            .filter(t -> t.getNumeroDeDescargas() == est.getMin())
            .map(Books::getTitulo)
            .findFirst()
            .orElse("No se encontro ninguno");


        System.out.println("----------------------------------");
    System.out.println("Estadistica de descargas de Libros");
    System.out.println("----------------------------------");
    System.out.println("Media de las Descargas: "+est.getAverage());
    System.out.println("Libro mas leido : " + libroMasDescargado +", Con un maximo de descargas: "+ est.getMax());
    System.out.println("Libro con menos leido: "+libroConMenosDescargas+", Con un minimo de descargas: "+est.getMin());
    System.out.println("Total descargas de libros: "+est.getSum());

}

private void top10librosMasDescargados(){
    var json = consumoApi.obtenerDatos(URL_BASE);
    Datos result = convierteDatos.obtenerDatos(json, Datos.class);

List<DatosBooks> top10Libros = result.datosLibrosList().stream()
        .sorted(Comparator.comparing(DatosBooks::numeroDeDescargas).reversed())
        .limit(10)
        .collect(Collectors.toList());

System.out.println("TOP 10 DE LIBROS MAS DESCARGADOS ");
    System.out.println("-------------------------------");
top10Libros.forEach(d -> System.out.println("Libro: "+d.titulo() + ", Numero de Descargas: "+d.numeroDeDescargas()));

    List<DatosBooks> top10menosLibros = result.datosLibrosList().stream()
            .sorted(Comparator.comparing(DatosBooks::numeroDeDescargas))
            .limit(10)
            .collect(Collectors.toList());
    System.out.println("-------------------------------");
    System.out.println("TOP 10 DE LIBROS MENOS DESCARGADOS");
    System.out.println("-------------------------------");
top10menosLibros.forEach(m -> System.out.println("Libros: "+m.titulo() + ", Numero de Descargas: "+m.numeroDeDescargas()));
}

private void buscarAutororApi()
{
    System.out.println("Por favor escribir el Nombre del Autor que desea Buscar: ");
    var nombreAutor = teclado.nextLine();

    var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreAutor.replace(" ","+"));
    Datos result = convierteDatos.obtenerDatos(json, Datos.class);

    List<DatosBooks> AutorBuscado = result.datosLibrosList().stream()
            .filter(l -> l.titulo().toUpperCase().contains(nombreAutor.toUpperCase()))
            .collect(Collectors.toList());




    if (!AutorBuscado.isEmpty()){
        AutorBuscado.forEach(a -> System.out.println("\n Libros: "+a.titulo() +
                "\n Autor: " + a.datosAutorList().stream().findFirst().map(DatosAutor::nombre).orElse("Autor no disponible") +
                "\n Idioma: "+a.idiomaList() +
                "\n Numero de Descargas: "+a.numeroDeDescargas()));
    }else {
        System.out.println("------------------------------------------------------------------");
        System.out.println("No existe Autor");
        System.out.println("------------------------------------------------------------------");
    }

}

}
