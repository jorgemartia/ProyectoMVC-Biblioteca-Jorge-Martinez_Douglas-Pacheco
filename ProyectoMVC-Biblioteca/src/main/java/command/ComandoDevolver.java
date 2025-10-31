package command;

import java.util.List;

import model.Catalogo;
import model.Libro;
import util.JsonStorage;
import util.SessionManager;
import util.Validacion;

public class ComandoDevolver implements Comando {
    private final String isbn;

    public ComandoDevolver(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public void ejecutar() {
        // Obtener usuario actual
        SessionManager session = SessionManager.getInstancia();
        String usuario = session != null ? session.getUsuarioActual() : null;
        
        if (usuario == null || usuario.trim().isEmpty()) {
            Validacion.mensajeusuarioautenticado();;
            return;
        }

        String id = isbn == null ? "" : isbn.trim();

        List<Libro> libros = JsonStorage.cargarLibros();

        // Buscar por título
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
            boolean ok = libro.devolver(usuario);
            if (ok) {
                JsonStorage.guardarLibros(libros);
                // actualizar el catálogo en memoria para que la vista refleje cambios
                Catalogo.getInstancia().recargarLibros();
                Validacion.mensajeLibroDevuelto(libro.getTitulo());
            } else {
                Validacion.mensajeDevolucionNOvalida(id);;
            }
        } catch (IllegalStateException ex) {
            Validacion.mostrarError(ex.getMessage());
        } catch (Exception ex) {
            Validacion.mensajeError();;
        }
    }
}