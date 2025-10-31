package util;

import model.Personas;

public class AuthService {
    private static final String CLAVE_ADMIN = "admin123";

    public enum Role { ADMIN, USUARIO, INVALID }

    public static Role verificarClave(String clave) {
        if (clave == null || clave.isEmpty()) {
            return Role.INVALID;
        }

        // Clave del administrador
        if (CLAVE_ADMIN.equals(clave)) {
            return Role.ADMIN;
        }

        // Buscar usuario en el JSON
        Personas usuario = Personas.buscarPorClave(clave);
        if (usuario != null) {
            return Role.USUARIO;
        }

        return Role.INVALID;
    }
}

