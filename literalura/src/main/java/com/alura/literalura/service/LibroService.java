package com.alura.literalura.service;


import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos convierteDatos = new ConvierteDatos();

    private final String URL_BASE = "https://gutendex.com/books/?search=";

    public LibroService(LibroRepository libroRepository,
                        AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {

        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {

            System.out.println("""
                    
                    ========= LITERALURA =========
                    1 - Buscar libro por título
             
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en determinado año
                    5 - Listar libros por idioma
                    
                    6 - Cantidad de libros por idioma
                    
                    0 - Salir
                    """);

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
                continue;
            }

            switch (opcion) {
                case 1 -> buscarLibro(scanner);
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> autoresVivos(scanner);
                case 5 -> listarPorIdioma(scanner);
                case 6 -> cantidadPorIdioma(scanner);
                case 0 -> System.out.println("Cerrando aplicacion...");
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    // =========================================
    // BUSCAR LIBRO POR TÍTULO
    // =========================================

    private void buscarLibro(Scanner scanner) {

        System.out.println("Ingrese el título del libro:");
        String titulo = scanner.nextLine();

        String json = consumoAPI.obtenerDatos(URL_BASE + titulo.replace(" ", "+"));

        DatosRespuesta datos =
                convierteDatos.obtenerDatos(json, DatosRespuesta.class);

        if (datos.resultados().isEmpty()) {
            System.out.println("El libro "+titulo+" no ha sido encontrado.");
            return;
        }

        DatosLibro datosLibro = datos.resultados().get(0);

        DatosAutor datosAutor = datosLibro.authors().get(0);

        Autor autor = new Autor();
        autor.setNombre(datosAutor.name());
        autor.setNacimiento(datosAutor.birthYear());
        autor.setFallecimiento(datosAutor.deathYear());

        autorRepository.save(autor);

        Libro libro = new Libro();
        libro.setTitulo(datosLibro.title());
        libro.setIdioma(datosLibro.languages().get(0));
        libro.setNumeroDescargas(datosLibro.downloadCount());
        libro.setAutor(autor);

        libroRepository.save(libro);

        System.out.println("El libro ha sido guardado.");
    }

    // =========================================
    // LISTAR LIBROS
    // =========================================

    private void listarLibros() {

        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    // =========================================
    // LISTAR AUTORES
    // =========================================

    private void listarAutores() {

        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        autores.forEach(System.out::println);
    }

    // =========================================
    // AUTORES VIVOS EN AÑO
    // =========================================

    private void autoresVivos(Scanner scanner) {

        System.out.println("Ingrese el año:");
        try {
            Integer año = Integer.parseInt(scanner.nextLine());

            List<Autor> autores = autorRepository
                    .findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(año, año);

            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en ese año.");
                return;
            }

            autores.forEach(System.out::println);

        } catch (NumberFormatException e) {
            System.out.println("Año inválido.");
        }
    }

    // =========================================
    // LIBROS POR IDIOMA
    // =========================================

    private void listarPorIdioma(Scanner scanner) {

        System.out.println("""
                Ingrese idioma:
                en - Inglés
                es - Español
                fr - Francés
                """);

        String idioma = scanner.nextLine();

        List<Libro> libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No hay libros en ese idioma.");
            return;
        }

        libros.forEach(System.out::println);
    }

    // =========================================
    // CANTIDAD POR IDIOMA
    // =========================================

    private void cantidadPorIdioma(Scanner scanner) {

        System.out.println("Ingrese idioma (en / es):");
        String idioma = scanner.nextLine();

        Long cantidad = libroRepository.countByIdioma(idioma);

        System.out.println("Cantidad de libros en " + idioma + ": " + cantidad);
    }
}
