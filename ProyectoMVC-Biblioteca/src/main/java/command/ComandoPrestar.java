package command;

import java.util.List;

import model.Catalogo;
import model.Libro;
import util.JsonStorage;
import util.SessionManager;
import util.Validacion;

public class ComandoPrestar implements Comando {
    private final String isbn;

    public ComandoPrestar(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public void ejecutar() {
        // obtener instancia de SessionManager y luego el usuario actual
        SessionManager session = SessionManager.getInstancia();
        String usuario = session != null ? session.getUsuarioActual() : null;
        if (usuario == null || usuario.trim().isEmpty()) usuario = "ANONIMO";

        String id = isbn == null ? "" : isbn.trim();

        List<Libro> libros = JsonStorage.cargarLibros();

        // Buscar por título (nombre) en lugar de isbn
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
            // actualizar el catálogo en memoria para que la vista refleje cambios
            Catalogo.getInstancia().recargarLibros();
            Validacion.mensajeLibroPrestado(libro.getTitulo());
        } catch (IllegalStateException ex) {
            Validacion.mostrarError(ex.getMessage());
        } catch (Exception ex) {
            Validacion.mensajeError();;
        }
    }
}
