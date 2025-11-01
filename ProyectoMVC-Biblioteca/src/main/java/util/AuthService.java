package util;

import model.Personas;
/**
 * Servicio de autenticación que verifica las credenciales
 * y determina el rol del usuario en el sistema.
 */
public class AuthService {
/** Clave maestra para acceso de administrador. */
    private static final String CLAVE_ADMIN = "admin123";

    /** Roles posibles dentro del sistema. */
    public enum Role { ADMIN, USUARIO, INVALID }
    /**
     * Verifica la clave ingresada y determina el rol del usuario.
     * @param clave Clave de acceso proporcionada.
     * @return Rol correspondiente: ADMIN, USUARIO o INVALID.
     */
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

