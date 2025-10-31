package command;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Libro;
import util.JsonStorage;
import util.Validacion;


public class ComandoAgregar implements Comando {
    private final String titulo;
    private final String autor;
    private final String isbn;
    private final String categoria;
    private final int cantidadTotal;

    private static final String RUTA_JSON = System.getProperty("user.home")
            + File.separator + "BibliotecaDatos"
            + File.separator + "libros.json";

    public ComandoAgregar(String titulo, String autor, String isbn, String categoria, int cantidadTotal) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.categoria = categoria;
        this.cantidadTotal = cantidadTotal;
    }

    @Override
    public void ejecutar() {
        if (!Validacion.campoNoVacio(titulo, "Título") ||
            !Validacion.campoNoVacio(autor, "Autor") ||
            !Validacion.campoNoVacio(isbn, "ISBN") ||
            !Validacion.campoNoVacio(categoria, "Categoría")) {
            return;
        }

        //validemos la cantidad de disponibles que sea mayor a 0
        if (cantidadTotal <= 0) {
            Validacion.mensajeCantidadInvalida(titulo);;
            return;
        }


        List<Libro> libros = JsonStorage.cargarLibros();

        boolean existe = libros.stream().anyMatch(l ->
                l.getTitulo().equalsIgnoreCase(titulo) ||
                l.getIsbn().equalsIgnoreCase(isbn)
        );

        if (existe) {
            Validacion.mensajeLibroYaExiste(titulo);
            return;
        }

        Libro libro = new Libro(titulo, autor, isbn, categoria, cantidadTotal);
        libros.add(libro);
        JsonStorage.guardarLibros(libros);
        guardarLibrosEnJSON(libros);

        Validacion.mensajeLibroAgregado(titulo);;
    }

    private void guardarLibrosEnJSON(List<Libro> lista) {
        try {
            File archivo = new File(RUTA_JSON);
            File carpeta = archivo.getParentFile();
            if (!carpeta.exists()) carpeta.mkdirs();

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, lista);
        } catch (IOException e) {
            Validacion.mensajeLibronoguardado(RUTA_JSON);;
        }
    }
}
