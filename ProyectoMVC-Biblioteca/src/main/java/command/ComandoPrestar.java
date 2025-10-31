package command;

import model.Libro;
import util.JsonStorage;
import util.Validacion;

import java.util.List;

public class ComandoPrestar implements Comando {
    private final String titulo;
    private final String usuarioPrestamo;

    public ComandoPrestar(String titulo, String usuario) {
        this.titulo = titulo;
        this.usuarioPrestamo = usuario;
    }

    @Override
    public void ejecutar() {
        if (!Validacion.campoNoVacio(titulo, "Título")) return;

        List<Libro> libros = JsonStorage.cargarLibros();
        Libro libro = libros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);

        if (!Validacion.existeLibro(titulo, libro)) return;

        if (!libro.isDisponible()) {
            Validacion.mensajeLibroNoDisponible(titulo);
            return;
        }

        libro.prestar(usuarioPrestamo);
        JsonStorage.actualizarLibro(libro);
        Validacion.mensajeLibroPrestado(titulo);
    }
}
