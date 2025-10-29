package command;

import model.Libro;
import util.Validacion;
import java.util.ArrayList;
import java.util.List;

public class ComandoAgregar implements Comando {
    private final String titulo;
    private final String autor;
    private final String isbn;
    private final String categoria;
    private static final List<Libro> libros = new ArrayList<>();

    public ComandoAgregar(String titulo, String autor, String isbn, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.categoria = categoria;
    }

    private boolean existeLibro(String titulo) {
        return libros.stream()
                    .anyMatch(l -> l.getTitulo().equalsIgnoreCase(titulo));
    }

    @Override
    public void ejecutar() {
        // Validar que ningún campo esté vacío
        if (!Validacion.campoNoVacio(titulo, "Título") || 
            !Validacion.campoNoVacio(autor, "Autor") ||
            !Validacion.campoNoVacio(isbn, "ISBN") ||
            !Validacion.campoNoVacio(categoria, "Categoría")) {
            return;
        }

        // Verificar que el libro no exista
        if (existeLibro(titulo)) {
            Validacion.mensajeLibroYaExiste(titulo);
            return;
        }

        // Crear y agregar el nuevo libro
        Libro libro = new Libro(titulo, autor);
        libro.setIsbn(isbn);
        libro.setCategoria(categoria);
        libros.add(libro);
        
        // Mostrar mensaje de éxito
        Validacion.mensajeLibroAgregado(titulo);
    }

    // Método para obtener la lista de libros (si es necesario)
    public static List<Libro> getLibros() {
        return new ArrayList<>(libros);
    }
}
