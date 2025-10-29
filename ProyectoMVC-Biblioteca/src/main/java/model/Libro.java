package model;

public class Libro {
    private String titulo;
    private String autor;
    private String isbn;
    private String categoria;
    private boolean disponible;

    // Constructor
    public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.disponible = true;
    }

    // Setters necesarios
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public boolean isDisponible() { return disponible; }

    public void prestar() { this.disponible = false; }
    public void devolver() { this.disponible = true; }

    @Override
    public String toString() {
        return String.format("Título: %s | Autor: %s | Disponible: %s", titulo, autor, disponible);
    }
}
