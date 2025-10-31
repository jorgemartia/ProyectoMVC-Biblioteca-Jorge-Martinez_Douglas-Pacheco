package util;

/**
 * Clase Singleton para manejar la sesión del usuario actual
 * Esta clase almacena el usuario autenticado y permite acceso global
 * desde cualquier parte de la aplicación
 */
public class SessionManager {
    private static SessionManager instancia;
    private String usuarioActual;
    private AuthService.Role rolActual;

    /**
     * Constructor privado para implementar patrón Singleton
     */
    private SessionManager() {
        // Constructor privado
    }

    /**
     * Obtiene la única instancia de SessionManager
     * @return La instancia única de SessionManager
     */
    public static SessionManager getInstancia() {
        if (instancia == null) {
            instancia = new SessionManager();
        }
        return instancia;
    }

    /**
     * Inicia sesión para un usuario
     * @param nombreUsuario El nombre del usuario que inicia sesión
     * @param rol El rol del usuario (ADMIN o USUARIO)
     */
    public void iniciarSesion(String nombreUsuario, AuthService.Role rol) {
        this.usuarioActual = nombreUsuario;
        this.rolActual = rol;
    }

    /**
     * Obtiene el nombre del usuario actual autenticado
     * @return El nombre del usuario o null si no hay sesión activa
     */
    public String getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Obtiene el rol del usuario actual
     * @return El rol del usuario o null si no hay sesión activa
     */
    public AuthService.Role getRolActual() {
        return rolActual;
    }

    /**
     * Verifica si hay un usuario autenticado actualmente
     * @return true si hay un usuario autenticado, false en caso contrario
     */
    public boolean hayUsuarioAutenticado() {
        boolean autenticado = usuarioActual != null && !usuarioActual.isEmpty();
        if (!autenticado) {
        }
        return autenticado;
    }

    /**
     * Cierra la sesión actual
     */
    public void cerrarSesion() {
        if (usuarioActual != null) {
        }
        this.usuarioActual = null;
        this.rolActual = null;
    }

    /**
     * Verifica si el usuario actual es administrador
     * @return true si el usuario es admin, false en caso contrario
     */
    public boolean esAdmin() {
        return rolActual == AuthService.Role.ADMIN;
    }

    /**
     * Método para depuración - muestra el estado actual de la sesión
     */
    public void mostrarEstadoSesion() {
        System.out.println("═══════════════════════════════════");
        System.out.println("Estado de la Sesión:");
        System.out.println("Usuario: " + (usuarioActual != null ? usuarioActual : "No autenticado"));
        System.out.println("Rol: " + (rolActual != null ? rolActual : "N/A"));
        System.out.println("═══════════════════════════════════");
    }
}