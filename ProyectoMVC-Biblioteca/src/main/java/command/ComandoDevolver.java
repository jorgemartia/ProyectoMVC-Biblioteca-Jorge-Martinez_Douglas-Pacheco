package command;

import model.Libro;
import util.JsonStorage;
import util.Validacion;
import java.util.List;

public class ComandoDevolver implements Comando {
    private final String titulo;
    private final String usuarioActual;

    public ComandoDevolver(String titulo, String usuarioActual) {
        this.titulo = titulo;
        this.usuarioActual = usuarioActual;
    }

    @Override
    public void ejecutar() {
        List<Libro> libros = JsonStorage.cargarLibros();
        Libro libro = libros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);

        if (libro == null) {
            Validacion.mensajeLibroNoEncontrado(titulo);
            return;
        }

        // ✅ Verificar si el libro está prestado (disponible = false significa prestado)
        if (libro.isDisponible()) {
            Validacion.mensajeLibroNoDisponible(titulo);
            return;
        }

        // ✅ Verificar si el usuario que lo devuelve es el mismo que lo prestó
        String usuarioPrestamo = libro.getUsuarioPrestamo();
        if (usuarioPrestamo == null || !usuarioPrestamo.equals(usuarioActual)) {
            Validacion.mostrarError("Solo el usuario '" + usuarioPrestamo + "' puede devolver este libro.");
            return;
        }

        // ✅ Marcar como disponible (devuelto)
        libro.setDisponible(true);
        libro.setUsuarioPrestamo(null);
        
        JsonStorage.actualizarLibro(libro);
        Validacion.mensajeLibroDevuelto(titulo);
    }
}