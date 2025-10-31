package command;

import model.Libro;
import util.JsonStorage;
import util.Validacion;

import java.util.List;

public class ComandoRegistrar implements Comando {
    private final String titulo;
    private final String autor;
    private final String isbn;
    private final String categoria;

    public ComandoRegistrar(String titulo, String autor, String isbn, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.categoria = categoria;
    }

    @Override
    public void ejecutar() {
        if (!Validacion.campoNoVacio(titulo, "Título") ||
            !Validacion.campoNoVacio(autor, "Autor") ||
            !Validacion.campoNoVacio(isbn, "ISBN") ||
            !Validacion.campoNoVacio(categoria, "Categoría")) {
            return;
        }

        List<Libro> libros = JsonStorage.cargarLibros();

        boolean existe = libros.stream().anyMatch(l ->
                l.getTitulo().equalsIgnoreCase(titulo)
        );

        if (existe) {
            Validacion.mensajeLibroYaExiste(titulo);
            return;
        }

        Libro libro = new Libro(titulo, autor, isbn, categoria, 1);
        libros.add(libro);
        JsonStorage.guardarLibros(libros);

        Validacion.mensajeLibroAgregado(titulo);
    }
}

