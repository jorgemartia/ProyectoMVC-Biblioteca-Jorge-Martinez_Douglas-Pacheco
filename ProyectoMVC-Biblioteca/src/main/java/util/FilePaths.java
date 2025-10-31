package util;

import java.io.File;
/**
 * Clase utilitaria que gestiona las rutas de archivos JSON del sistema.
 * Crea automáticamente la carpeta <code>data</code> si no existe.
 */
public class FilePaths {
    /** Directorio base donde se almacenan los archivos JSON. */
    private static final String DATA_DIR = System.getProperty("user.dir") + 
            File.separator + "data";
    // Bloque estático que garantiza la creación del directorio de datos        
    static {
        // Crear el directorio data si no existe
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    /**
     * Obtiene la ruta completa del archivo de libros.
     * @return Ruta del archivo <code>libros.json</code>.
     */    
    public static String getLibrosPath() {
        return DATA_DIR + File.separator + "libros.json";
    }
    /**
     * Obtiene la ruta completa del archivo de usuarios.
     * @return Ruta del archivo <code>usuarios.json</code>.
     */    
    public static String getUsuariosPath() {
        return DATA_DIR + File.separator + "usuarios.json";
    }
    /**
     * Obtiene la ruta base del directorio de datos.
     * @return Ruta absoluta del directorio <code>data</code>.
     */
    public static String getDataDir() {
        return DATA_DIR;
    }
}
