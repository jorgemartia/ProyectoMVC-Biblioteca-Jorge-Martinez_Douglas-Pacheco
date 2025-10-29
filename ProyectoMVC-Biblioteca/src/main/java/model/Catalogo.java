package model;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private static Catalogo instancia;
    private final List<Libro> libros;

    private Catalogo() {
        libros = new ArrayList<>();
    }

    public static Catalogo getInstancia() {
        if (instancia == null) {
            instancia = new Catalogo();
        }
        return instancia;
    }

    

    public Libro buscarPorTitulo(String titulo) {
        for (Libro l : libros) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) return l;
        }
        return null;
    }

    public List<Libro> listarLibros() { return new ArrayList<>(libros); }
}