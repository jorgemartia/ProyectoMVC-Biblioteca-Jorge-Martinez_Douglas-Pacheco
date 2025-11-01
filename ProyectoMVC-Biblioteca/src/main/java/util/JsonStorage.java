package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Libro;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria para manejar la persistencia de libros en formato JSON.
 * Carga y actualiza el catálogo de libros manteniendo su estado
 * incluso después de cerrar la aplicación.
 */
public class JsonStorage {

    private static final String RUTA_JSON = util.FilePaths.getLibrosPath();

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Carga los libros desde el archivo JSON.
     * Si no existe, devuelve una lista vacía.
     */
    public static List<Libro> cargarLibros() {
        File archivo = new File(RUTA_JSON);

        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<ArrayList<Libro>>() {
            }.getType();
            List<Libro> libros = gson.fromJson(reader, tipoLista);
            return (libros != null) ? libros : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Guarda (reescribe) toda la lista de libros en el archivo JSON.
     * Se usa principalmente cuando se actualiza el estado de préstamo o devolución.
     */
    public static void guardarLibros(List<Libro> libros) {
        try {
            File archivo = new File(RUTA_JSON);
            File carpeta = archivo.getParentFile();

            if (!carpeta.exists())
                carpeta.mkdirs();

            try (FileWriter writer = new FileWriter(archivo)) {
                gson.toJson(libros, writer);
            }

        } catch (IOException e) {
            Validacion.mensajeLibronoguardado(RUTA_JSON);
        }
    }

    /**
     * Actualiza un libro existente dentro del archivo JSON,
     * manteniendo los demás registros sin alterarse.
     */
    public static void actualizarLibro(Libro libroActualizado) {
        List<Libro> libros = cargarLibros();
        boolean encontrado = false;

        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getTitulo().equalsIgnoreCase(libroActualizado.getTitulo())) {
                libros.set(i, libroActualizado);
                encontrado = true;
                break;
            }
        }

        // Si no existía, lo agrega
        if (!encontrado) {
            libros.add(libroActualizado);
        }

        guardarLibros(libros);
    }
}
