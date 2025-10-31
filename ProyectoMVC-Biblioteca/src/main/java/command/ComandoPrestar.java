package command;

import java.util.List;

import model.Catalogo;
import model.Libro;
import util.JsonStorage;
import util.SessionManager;
import util.Validacion;
/**
 * comando que permite prestar un libro a un usuario autenticado o anonimo.
 */
public class ComandoPrestar implements Comando {
    private final String isbn;

    public ComandoPrestar(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Ejecuta el préstamo del libro:
     * <ul>
     *   <li>Obtiene el usuario actual de la sesión.</li>
     *   <li>Busca el libro por título.</li>
     *   <li>Intenta prestarlo y actualiza los datos guardados.</li>
     * </ul>
     */
    @Override
    public void ejecutar() {
        SessionManager session = SessionManager.getInstancia();
        String usuario = session != null ? session.getUsuarioActual() : null;
        if (usuario == null || usuario.trim().isEmpty()) usuario = "ANONIMO";

        String id = isbn == null ? "" : isbn.trim();

        List<Libro> libros = JsonStorage.cargarLibros();

        Libro libro = libros.stream()
                .filter(l -> {
                    String ltit = l.getTitulo() == null ? "" : l.getTitulo().trim();
                    return ltit.equalsIgnoreCase(id);
                })
                .findFirst()
                .orElse(null);

        if (libro == null) {
            Validacion.mensajeLibroNoEncontrado(id);;
            return;
        }

        try {
            libro.prestar(usuario);
            JsonStorage.guardarLibros(libros);
            Catalogo.getInstancia().recargarLibros();
            Validacion.mensajeLibroPrestado(libro.getTitulo());
        } catch (IllegalStateException ex) {
            Validacion.mostrarError(ex.getMessage());
        } catch (Exception ex) {
            Validacion.mensajeError();;
        }
    }
}
