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
    private static final String RUTA_JSON = System.getProperty("user.home") 
            + File.separator + "BibliotecaDatos" 
            + File.separator + "libros.json";

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
     * Debe llamarse después de cada operación que modifique el catálogo
     */
    public void recargarLibros() {
        this.libros = cargarLibros();
    }

    public Libro buscarPorTitulo(String titulo) {
        // ✅ Recargar antes de buscar para tener datos actualizados
        recargarLibros();
        return libros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    private List<Libro> cargarLibros() {
        try {
            File archivo = new File(RUTA_JSON);
            if (!archivo.exists()) {
                return new ArrayList<>();
            }
            ObjectMapper mapper = new ObjectMapper();
            List<Libro> cargados = mapper.readValue(archivo, new TypeReference<List<Libro>>() {});
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