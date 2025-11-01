package command;

import java.util.List;

import model.Libro;
import util.JsonStorage;
import util.Validacion;
/**
 * Comando que permite registrar un nuevo libro en el sistema.
 * 
 * <p>Valida los campos ingresados, verifica que el libro no exista
 * y lo agrega a la lista persistente si es válido.</p>
 */
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
    /**
     * Ejecuta el registro del libro:
     * <ul>
     *   <li>Valida que todos los campos estén completos.</li>
     *   <li>Verifica si el libro ya existe en la base de datos.</li>
     *   <li>Crea el nuevo libro y lo guarda en el almacenamiento JSON.</li>
     *   <li>Muestra un mensaje de confirmación o error según el resultado.</li>
     * </ul>
     */
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

