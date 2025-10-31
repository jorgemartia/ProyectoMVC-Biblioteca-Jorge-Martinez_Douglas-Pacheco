package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Libro {
    private static final String RUTA_JSON = System.getProperty("user.home")
            + File.separator + "BibliotecaDatos"
            + File.separator + "libros.json";

    @JsonProperty("titulo")
    private String titulo;
    @JsonProperty("autor")
    private String autor;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("categoria")
    private String categoria;
    @JsonProperty("disponible")
    private boolean disponible;
    private String usuarioPrestamo;

    // Constructor vacío para Jackson
    public Libro() {
    }

    // Constructor con parámetros
    public Libro(String titulo, String autor, String isbn, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.categoria = categoria;
        this.disponible = true;
    }

    // Getters y setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getUsuarioPrestamo() {
        return usuarioPrestamo;
    }

    public void setUsuarioPrestamo(String usuarioPrestamo) {
        this.usuarioPrestamo = usuarioPrestamo;
    }

    // 🔹 Guardar libro en el JSON
    public void guardarEnJSON() {
        try {
            File archivo = new File(RUTA_JSON);
            File carpeta = archivo.getParentFile();
            if (!carpeta.exists())
                carpeta.mkdirs();

            ObjectMapper mapper = new ObjectMapper();
            List<Libro> lista;

            // Si el archivo ya existe, cargar los libros existentes
            if (archivo.exists()) {
                lista = mapper.readValue(archivo, new TypeReference<List<Libro>>() {
                });
            } else {
                lista = new ArrayList<>();
            }

            // Evitar duplicados (por ISBN o título)
            boolean existe = lista.stream().anyMatch(l -> l.getIsbn().equalsIgnoreCase(this.isbn) ||
                    l.getTitulo().equalsIgnoreCase(this.titulo));

            if (!existe) {
                lista.add(this);
                mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, lista);
                System.out.println("✅ Libro guardado correctamente en: " + archivo.getAbsolutePath());
            } else {
                System.out.println("⚠️ El libro con título o ISBN ya existe.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Cargar todos los libros
    public static List<Libro> cargarTodos() {
        try {
            File archivo = new File(RUTA_JSON);
            if (!archivo.exists())
                return new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(archivo, new TypeReference<List<Libro>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 🔹 Buscar libro por ISBN
    public static Libro buscarPorIsbn(String isbn) {
        return cargarTodos().stream()
                .filter(l -> l.getIsbn().equalsIgnoreCase(isbn))
                .findFirst()
                .orElse(null);
    }

    // 🔹 Buscar libro por título
    public static Libro buscarPorTitulo(String titulo) {
        return cargarTodos().stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    public void prestar(String usuario) {
        this.disponible = false;
        this.usuarioPrestamo = usuario;
    }

    public boolean devolver(String usuario) {
        // Solo el mismo usuario puede devolverlo
        if (usuarioPrestamo != null && usuarioPrestamo.equalsIgnoreCase(usuario)) {
            this.disponible = true;
            this.usuarioPrestamo = null;
            return true;
        }
        return false; // No autorizado
    }

}
