package util;

import java.awt.Frame;
import javax.swing.JOptionPane;

import model.Catalogo;
import model.Libro;
import view.LoginDialog;

public final class Validacion {
  private static final String TITULO = "Biblioteca";

    private Validacion() {}

    // Mensajes con JOptionPane usando título genérico "Biblioteca"
    private static void mostrarInfoInterno(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarErrorInterno(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.ERROR_MESSAGE);
    }

    private static void mostrarAdvertenciaInterno(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.WARNING_MESSAGE);
    }

    public static void mostrarInfo(String mensaje) { mostrarInfoInterno(mensaje); }
    public static void mostrarError(String mensaje) { mostrarErrorInterno(mensaje); }
    public static void mostrarAdvertencia(String mensaje) { mostrarAdvertenciaInterno(mensaje); }

    // Mensajes específicos (evitan concatenación fuera de aquí)
    public static void mensajeLibroAgregado(String titulo) { mostrarInfo("El libro '" + titulo + "' ha sido agregado correctamente."); }
    public static void mensajeLibroPrestado(String titulo) { mostrarInfo("El libro '" + titulo + "' fue prestado."); }
    public static void mensajeregistroexitoso() { mostrarInfo("Registro Exitoso"); }
    public static void mensajeLibroDevuelto(String titulo) { mostrarInfo("El libro '" + titulo + "' fue devuelto."); }
    public static void mensajeLibroYaExiste(String titulo) { mostrarError("El libro '" + titulo + "' ya existe en el catálogo."); }
    public static void mensajeLibroNoEncontrado(String titulo) { mostrarError("No se encontró el libro '" + titulo + "' en el catálogo."); }
    public static void mensajeLibroNoDisponible(String titulo) { mostrarError("El libro '" + titulo + "' no está disponible para prestar."); }
    public static void mensajeLibroYaDisponible(String titulo) { mostrarAdvertencia("El libro '" + titulo + "' ya está disponible."); }
    public static void mensajeCampoVacio(String campo) { mostrarError("El campo '" + campo + "' no puede estar vacío."); }
    public static void mensajeCatalogoVacio() { mostrarInfo("El catálogo está vacío."); }
    public static void mensajecamposcompletos() { mostrarAdvertencia("complete todo los campos."); }

    // Validaciones
    public static boolean campoNoVacio(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            mensajeCampoVacio(nombreCampo);
            return false;
        }
        return true;
    }

    public static boolean noExisteLibroEnCatalogo(String titulo, Catalogo c) {
        Libro l = c.buscarPorTitulo(titulo);
        if (l != null) {
            mensajeLibroYaExiste(titulo);
            return false;
        }
        return true;
    }

    public static boolean existeLibro(String titulo, Libro l) {
        if (l == null) {
            mensajeLibroNoEncontrado(titulo);
            return false;
        }
        return true;
    }

    public static boolean estaDisponible(Libro l) {
        if (!l.isDisponible()) {
            mensajeLibroNoDisponible(l.getTitulo());
            return false;
        }
        return true;
    }

    // Autenticación con diálogo: maneja intentos y mensajes.
    // Retorna el rol identificado (ADMIN, USUARIO) o INVALID si no se logró autenticación.
    public static AuthService.Role autenticarConDialog(Frame parent, int maxAttempts) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            attempts++;
            LoginDialog login = new LoginDialog(parent);
            login.setVisible(true);
            if (!login.isConfirmed()) {
                // usuario canceló
                mostrarInfoInterno("Inicio de sesión cancelado.");
                return AuthService.Role.INVALID;
            }
            String clave = login.getClave();
            AuthService.Role role = AuthService.verificarClave(clave);
            if (role == AuthService.Role.INVALID) {
                mostrarErrorInterno("Clave inválida. Intenta de nuevo.");
                continue;
            }
            // autenticación exitosa
            mostrarInfoInterno("Inicio de sesión exitoso.");
            return role;
        }
        // superó intentos permitidos
        mostrarAdvertenciaInterno("Se superó el número máximo de intentos.");
        return AuthService.Role.INVALID;
    }

    // Autentica y si falla cierra la aplicación mostrando el mensaje correspondiente.
    public static AuthService.Role autenticarOExit(Frame parent, int maxAttempts) {
        AuthService.Role role = autenticarConDialog(parent, maxAttempts);
        if (role == AuthService.Role.INVALID) {
            mostrarInfoInterno("No se ha iniciado sesión. La aplicación se cerrará.");
            System.exit(0);
        }
        return role;
    }
}
