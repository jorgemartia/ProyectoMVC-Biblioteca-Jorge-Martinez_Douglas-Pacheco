package command;

import java.util.List;

import model.Catalogo;
import model.Libro;
import util.JsonStorage;
import util.SessionManager;
import util.Validacion;

public class ComandoDevolver implements Comando {
    private final String nombre;
    private final String autor;
/**
 * comando que permite devolver un libro prestado por un usuario autenticado.
 * @param nombre
 */
    public ComandoDevolver(String nombre, String autor) {
        this.nombre = nombre;
        this.autor = autor;
    }
/**
     * Ejecuta la devolución del libro:
     * <ul>
     *   <li>Verifica si el usuario está autenticado.</li>
     *   <li>Busca el libro por título.</li>
     *   <li>Intenta devolverlo y actualiza los datos.</li>
     * </ul>
     */
    @Override
    public void ejecutar() {
        SessionManager session = SessionManager.getInstancia();
        String usuario = session != null ? session.getUsuarioActual() : null;
        
        if (usuario == null || usuario.trim().isEmpty()) {
            Validacion.mensajeusuarioautenticado();;
            return;
        }

        String tituloBusqueda = nombre == null ? "" : nombre.trim();
        String autorBusqueda = autor == null ? "" : autor.trim();

        if (tituloBusqueda.isEmpty() || autorBusqueda.isEmpty()) {
            Validacion.mensajecamposcompletos();;
            return;
        }

        List<Libro> libros = JsonStorage.cargarLibros();

        Libro libro = libros.stream()
                .filter(l -> {
                    String ltit = l.getTitulo() == null ? "" : l.getTitulo().trim();
                    String laut = l.getAutor() == null ? "" : l.getAutor().trim();
                    return ltit.equalsIgnoreCase(tituloBusqueda)&& laut.equalsIgnoreCase(autorBusqueda);
                })
                .findFirst()
                .orElse(null);

        if (libro == null) {
            Validacion.mensajeLibroNoEncontrado(tituloBusqueda);;
            return;
        }

        try {
            boolean ok = libro.devolver(usuario);
            if (ok) {
                JsonStorage.guardarLibros(libros);
                Catalogo.getInstancia().recargarLibros();
                Validacion.mensajeLibroDevuelto(libro.getTitulo());
            } else {
                Validacion.mensajeDevolucionNOvalida(tituloBusqueda);;
            }
        } catch (IllegalStateException ex) {
            Validacion.mostrarError(ex.getMessage());
        } catch (Exception ex) {
            Validacion.mensajeError();;
        }
    }
}