package util;

import model.Personas;


import java.io.File;
import java.io.IOException;


public class AuthService {
    private static final String CLAVE_ADMIN = "admin123";
    private static final String R_STRING = "usuarios.json"; // misma ruta usada en Personas.guardarEnJSON()

    public enum Role { ADMIN, USUARIO, INVALID }

    public static Role verificarClave(String clave) {
        if (clave == null || clave.isEmpty()) {
            return Role.INVALID;
        }

        // Si es la clave del admin
        if (CLAVE_ADMIN.equals(clave)) {
            return Role.ADMIN;
        }

        // Si la clave pertenece a un usuario del JSON
        if (existeClaveEnJSON(clave)) {
            return Role.USUARIO;
        }

        return Role.INVALID;
    }

    private static boolean existeClaveEnJSON(String clave) {
    File carpeta = new File("datos");
    if (!carpeta.exists()) return false;

    File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".json"));
    if (archivos == null) return false;

    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
    for (File f : archivos) {
        try {
            Personas p = mapper.readValue(f, Personas.class);
            if (p.getClave().equals(clave)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return false;
}
}

