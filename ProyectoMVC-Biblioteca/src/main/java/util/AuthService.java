package util;

public class AuthService {
    // Claves simples hardcodeadas para demostración.
    // En producción reemplazar por almacenamiento seguro.
    private static final String CLAVE_ADMIN = "admin123";
    private static final String CLAVE_USUARIO = "user123";

    public enum Role { ADMIN, USUARIO, INVALID }

    public static Role verificarClave(String clave) {
        if (clave == null) return Role.INVALID;
        if (CLAVE_ADMIN.equals(clave)) return Role.ADMIN;
        if (CLAVE_USUARIO.equals(clave)) return Role.USUARIO;
        return Role.INVALID;
    }
}
