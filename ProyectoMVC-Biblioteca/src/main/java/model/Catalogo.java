package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import util.JsonStorage;

public class Catalogo {
    private static Catalogo instancia;
    private List<Libro> libros;

    private static final String RUTA_JSON = util.FilePaths.getLibrosPath();

    private Catalogo() {
        this.libros = cargarLibros();
    }

    public static Catalogo getInstancia() {
        if (instancia == null) {
            instancia = new Catalogo();
        }
        return instancia;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    /**
     * ✅ Método para recargar los libros desde el archivo JSON
     */
    public void recargarLibros() {
        this.libros = cargarLibros();
    }

    public Libro buscarPorTitulo(String titulo) {
        recargarLibros();
        return libros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    /**
     * ✅ Obtiene todos los préstamos activos del sistema
     */
    public List<Object[]> getPrestamosActivos() {
        recargarLibros();
        List<Object[]> prestamos = new ArrayList<>();

        for (Libro libro : libros) {
            List<String> usuariosPrestamo = libro.getUsuariosConPrestamos();
            for (String usuario : usuariosPrestamo) {
                prestamos.add(new Object[] {
                        usuario,
                        obtenerIdUsuario(usuario),
                        libro.getTitulo(),
                        libro.getAutor(),
                        libro.getIsbn(),
                        libro.getCategoria(),
                        1
                });
            }
        }
        return prestamos;
    }

    /**
     * ✅ Obtiene el catálogo completo con todos los detalles
     */
    public List<Object[]> getCatalogoCompleto() {
        recargarLibros();
        List<Object[]> catalogo = new ArrayList<>();

        for (Libro libro : libros) {
            catalogo.add(new Object[] {
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIsbn(),
                    libro.getCategoria(),
                    libro.getCantidadTotal(),
                    libro.getCantidadDisponible(),
                    libro.isDisponible() ? "Sí" : "No"
            });
        }
        return catalogo;
    }

    /**
     * ✅ Simula obtener el ID del usuario (en un sistema real vendría de la base de
     * usuarios)
     */
    private String obtenerIdUsuario(String usuario) {
        return "USER-" + Math.abs(usuario.hashCode() % 10000);
    }

    private List<Libro> cargarLibros() {
        try {
            File archivo = new File(RUTA_JSON);
            if (!archivo.exists()) {
                return new ArrayList<>();
            }
            ObjectMapper mapper = new ObjectMapper();
            List<Libro> cargados = mapper.readValue(archivo, new TypeReference<List<Libro>>() {
            });
            return cargados != null ? cargados : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void limpiar() {
        libros.clear();
        JsonStorage.guardarLibros(libros);
    }
}