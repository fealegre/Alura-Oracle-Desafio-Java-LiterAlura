package com.alura.bookmatch.principal;

import com.alura.bookmatch.model.*;
import com.alura.bookmatch.repository.AutorRepository;
import com.alura.bookmatch.repository.LibroRepository;
import com.alura.bookmatch.service.ConsumoAPI;
import com.alura.bookmatch.service.ConvierteDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

public class Principal {
    private Scanner ingreso = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private String[] idiomas= {"en","es","ca","pt","de","fi","hu","la","fr"};

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository){
        this.libroRepository=libroRepository;
        this.autorRepository=autorRepository;
    }



    public void muestraElMenu() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \t\t****************************************
                    \t\t* MENU DE OPCIONES (ingresa un número) *
                    \t\t****************************************
                    (1)\t -\t Buscar libro por título
                    (2)\t -\t Mostrar lists de libros registrados
                    (3)\t -\t Mostrar lista de autores registrados
                    (4)\t -\t Mostrar lista de autores nacidos en un año determinado
                    (5)\t -\t Mostrar lista de libros por idioma
                    \n
                    (0)\t -\t Salir
                    """;
            System.out.println(menu);
            opcion = ingreso.nextInt();
            ingreso.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibros();
                    break;
                case 2:
                    mostrarListaLibrosRegistrados();
                    break;
                case 3:
                    mostrarListaAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresPorFechaNacimiento();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma de los libros que desea ver:");
        System.out.println(("en: Inglés | es: Español | ca: Catalán | pt: Portugués | de: Alemán | fi: Finés | hu: Húngaro | la: Latín | fr: Francés"));
        var opcion = ingreso.nextLine();
        if (Arrays.asList(idiomas).contains(opcion)){
            libroRepository.findByIdiomas(opcion).forEach(l-> System.out.printf("Título: %s | Idioma: %s\n",l.getTitulo(),l.getIdiomas()));
        } else {
            System.out.println("Idioma no encontrado");
        }
    }

    private void mostrarAutoresPorFechaNacimiento() {
        System.out.println("Ingrese el año para listar autores nacidos:");
        var fnac = ingreso.nextInt();
        System.out.printf("AUTORES NACIDOS ENTRE %d y %d:\n",fnac,fnac+50);
        System.out.println("******************************");
        autorRepository.listaAutoresPoFechaNacimiento(fnac).forEach(autor -> System.out.println(autor.getNombre()));
    }

    private void mostrarListaAutoresRegistrados() {
        System.out.println("*************************************\nLISTA DE AUTORES REGISTRADOS EN LA BD\n*************************************");
        autorRepository.findAll()
                .forEach(a->System.out.printf("NOMBRE: %s\t| AÑO NACIMIENTO: %d\t| AÑO FALLECIMIENTO: %d\n",a.getNombre(),a.getFechaNacimiento(),a.getFechaDeceso()));
    }

    private void mostrarListaLibrosRegistrados() {
        System.out.println("*************************************\nLISTA DE LIBROS REGISTRADOS EN LA BD\n*************************************");
        libroRepository.findAll().
                forEach(l->System.out.printf("TITULO: %s\t| IDIOMA: %s\t| DESCARGAS: %.1f\n",l.getTitulo(),l.getIdiomas(),l.getDescargas()));
    }

    public void buscarLibros(){
        System.out.println("Ingrese el nombre del libro o autor a buscar:");
        var libroBuscado = ingreso.nextLine();
        var json = consumoAPI.obtenerDatosLibros(URL_BASE+"?search="+libroBuscado.replace(" ","%20"));
        System.out.println(json);
        var datos =convierteDatos.obtenerDatos(json, LibroItem.class);
        for (DatosLibro dt : datos.datosLibrosList()) {
            Libro libro = new Libro(dt);
            List<Autor> libroAutores = new ArrayList<>();
            dt.autores().forEach(datosAutor -> libroAutores.add(new Autor(datosAutor)));
            libro.setAutores(libroAutores);
            try {
                libroRepository.save(libro);
            } catch (DataIntegrityViolationException e) {
                System.out.println("ERROR: No se pudo guardar el registro" + e.getMessage());                            }
        }
        }
    }

