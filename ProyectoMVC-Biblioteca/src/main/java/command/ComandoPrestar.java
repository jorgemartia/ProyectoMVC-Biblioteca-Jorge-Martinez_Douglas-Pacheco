package command;

import java.util.List;

import model.Catalogo;
import model.Libro;
import util.JsonStorage;
import util.SessionManager;
import util.Validacion;

/**
 * Comando que permite prestar un libro a un usuario autenticado o anónimo.
 * El usuario puede prestar varios libros diferentes pero no más de un ejemplar
 * igual.
 */
public class ComandoPrestar implements Comando {
    private final String titulo;

    public ComandoPrestar(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Ejecuta el préstamo del libro:
     * <ul>
     * <li>Obtiene el usuario actual de la sesión.</li>
     * <li>Busca el libro por título.</li>
     * <li>Verifica que el usuario no tenga ya prestado este libro.</li>
     * <li>Intenta prestarlo y actualiza los datos guardados.</li>
     * </ul>
     */
    @Override
    public void ejecutar() {
        SessionManager session = SessionManager.getInstancia();
        String usuario = session != null ? session.getUsuarioActual() : null;
        if (usuario == null || usuario.trim().isEmpty())
            usuario = "ANONIMO";

        String tituloBusqueda = titulo == null ? "" : titulo.trim();

        if (tituloBusqueda.isEmpty()) {
            Validacion.mensajecamposcompletos();
            return;
        }

        List<Libro> libros = JsonStorage.cargarLibros();

        Libro libro = libros.stream()
                .filter(l -> {
                    String tituloLibro = l.getTitulo() == null ? "" : l.getTitulo().trim();
                    return tituloLibro.equalsIgnoreCase(tituloBusqueda);
                })
                .findFirst()
                .orElse(null);

        if (libro == null) {
            Validacion.mensajeLibroNoEncontrado(tituloBusqueda);
            return;
        }

        // VERIFICAR SI EL USUARIO YA TIENE ESTE LIBRO PRESTADO
        if (usuarioYaTieneLibroPrestado(libro, usuario)) {
            Validacion.ejemplarPrestado(tituloBusqueda);
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
            Validacion.mensajeError();
        }
    }

    /**
     * Verifica si el usuario ya tiene este libro prestado.
     * 
     * @param libro   El libro a verificar
     * @param usuario El usuario a verificar
     * @return true si el usuario ya tiene este libro prestado, false en caso
     *         contrario
     */
    private boolean usuarioYaTieneLibroPrestado(Libro libro, String usuario) {
        if (libro.getUsuariosPrestamo() == null) {
            return false;
        }

        return libro.getUsuariosPrestamo().stream()
                .anyMatch(usuarioPrestamo -> usuarioPrestamo.equals(usuario));
    }
}