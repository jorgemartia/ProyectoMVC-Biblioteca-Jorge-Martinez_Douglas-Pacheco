package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import util.Validacion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Libro {

    private static final String RUTA_JSON = util.FilePaths.getLibrosPath();

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

    // Reemplazado el campo único por una lista de usuarios
    @JsonProperty("usuariosPrestamo")
    private List<String> usuariosPrestamo = new ArrayList<>();

    @JsonProperty("cantidadTotal")
    private int cantidadTotal;
    @JsonProperty("cantidadDisponible")
    private int cantidadDisponible;

    // Constructor vacío para Jackson
    public Libro() {
        this.cantidadTotal = 1;
        this.cantidadDisponible = 1;
        this.usuariosPrestamo = new ArrayList<>();
        this.disponible = true;
    }

    // Constructor con parámetros
    public Libro(String titulo, String autor, String isbn, String categoria, int cantidadTotal) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.categoria = categoria;
        this.cantidadTotal = Math.max(1, cantidadTotal);
        this.cantidadDisponible = this.cantidadTotal;
        this.usuariosPrestamo = new ArrayList<>();
        this.disponible = this.cantidadDisponible > 0;
    }

    // Getters / setters mínimos relevantes
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCategoria() {
        return categoria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = Math.max(1, cantidadTotal);
        if (this.cantidadDisponible > this.cantidadTotal) {
            this.cantidadDisponible = this.cantidadTotal;
        }
        this.disponible = this.cantidadDisponible > 0;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = Math.max(0, Math.min(cantidadDisponible, this.cantidadTotal));
        this.disponible = this.cantidadDisponible > 0;
    }

    public List<String> getUsuariosPrestamo() {
        if (usuariosPrestamo == null)
            usuariosPrestamo = new ArrayList<>();
        return usuariosPrestamo;
    }

    public void setUsuariosPrestamo(List<String> usuariosPrestamo) {
        this.usuariosPrestamo = usuariosPrestamo == null ? new ArrayList<>() : usuariosPrestamo;
    }

    // Prestar: añade usuario y decrementa disponibilidad (thread-safe)
    public synchronized void prestar(String usuario) {
        if (usuario == null)
            usuario = "DESCONOCIDO";
        if (cantidadDisponible <= 0) {
            Validacion.mensajeLibroNoDisponible(usuario);
        }
        // permitir que el mismo usuario tome más de una copia si es necesario
        usuariosPrestamo.add(usuario);
        cantidadDisponible--;
        disponible = cantidadDisponible > 0;
    }

    // Devolver: elimina una ocurrencia del usuario; si no está, permite devolución
    // "anónima" hasta no superar total
    public synchronized boolean devolver(String usuario) {
        if (usuario == null) {
            Validacion.mensajeusuarioautenticado();
        }

        // Verificar si el usuario tiene préstamos activos de este libro
        boolean usuarioTienePrestamo = false;
        if (usuariosPrestamo != null) {
            // Contar cuántas veces aparece el usuario en la lista
            long count = usuariosPrestamo.stream()
                    .filter(u -> u.equals(usuario))
                    .count();
            usuarioTienePrestamo = count > 0;
        }

        if (!usuarioTienePrestamo) {
            Validacion.mensajeDevolucionNOvalida(usuario);
        }

        // Remover una ocurrencia del usuario
        boolean removed = false;
        if (usuariosPrestamo != null) {
            for (int i = 0; i < usuariosPrestamo.size(); i++) {
                if (usuariosPrestamo.get(i).equals(usuario)) {
                    usuariosPrestamo.remove(i);
                    removed = true;
                    break;
                }
            }
        }

        if (removed) {
            if (cantidadDisponible < cantidadTotal) {
                cantidadDisponible++;
                disponible = cantidadDisponible > 0;
            }
            return true;
        } else {
            // Esto no debería pasar si ya verificamos que tiene préstamos
            return false;
        }
    }

    // 🔹 Guardar libro en el JSON
    public void guardarEnJSON() {
        try {
            File archivo = new File(RUTA_JSON);
            File carpeta = archivo.getParentFile();
            if (!carpeta.exists())
                carpeta.mkdirs();

            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            List<Libro> lista;

            // Si el archivo ya existe, cargar los libros existentes
            if (archivo.exists()) {
                lista = mapper.readValue(archivo, new com.fasterxml.jackson.core.type.TypeReference<List<Libro>>() {
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
                Validacion.mensajeLibroAgregado(this.titulo);
            } else {
                Validacion.mensajeLibroYaExiste(this.titulo);
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Cargar todos los libros
    public static List<Libro> cargarTodos() {
        try {
            File archivo = new File(RUTA_JSON);
            if (!archivo.exists())
                return new ArrayList<>();

            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(archivo, new com.fasterxml.jackson.core.type.TypeReference<List<Libro>>() {
            });
        } catch (java.io.IOException e) {
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

    // En la clase Libro, agregar estos métodos:

    /**
     * Obtiene la cantidad de ejemplares prestados a un usuario específico
     */
    public int getCantidadPrestadaAUsuario(String usuario) {
        if (usuariosPrestamo == null)
            return 0;
        return (int) usuariosPrestamo.stream()
                .filter(u -> u.equals(usuario))
                .count();
    }

    /**
     * Obtiene todos los usuarios que tienen préstamos activos de este libro
     */
    public List<String> getUsuariosConPrestamos() {
        if (usuariosPrestamo == null)
            return new ArrayList<>();
        return new ArrayList<>(usuariosPrestamo);
    }

    /**
     * Obtiene la cantidad total de préstamos activos
     */
    public int getTotalPrestamosActivos() {
        return usuariosPrestamo != null ? usuariosPrestamo.size() : 0;
    }

}
