package util;

import java.io.File;

public class FilePaths {
    private static final String DATA_DIR = System.getProperty("user.dir") + 
            File.separator + "data";
    
    static {
        // Crear el directorio data si no existe
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    public static String getLibrosPath() {
        return DATA_DIR + File.separator + "libros.json";
    }
    
    public static String getUsuariosPath() {
        return DATA_DIR + File.separator + "usuarios.json";
    }
    
    public static String getDataDir() {
        return DATA_DIR;
    }
}
