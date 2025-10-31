package util;

import java.awt.Frame;


import model.Catalogo;
import model.Libro;
import view.LoginView;
import view.RegistroView;

public final class Validacion {
    

    private Validacion() {
    }

    // =======================
    // 🔹 Métodos de mensajes
    // =======================
    public static void mostrarInfo(String mensaje) {
        Diseno.mostrarMensajeInfo(null, mensaje);
    }

    public static void mostrarError(String mensaje) {
        Diseno.mostrarMensajeError(null, mensaje);
    }

    public static void mostrarAdvertencia(String mensaje) {
        Diseno.mostrarMensajeAdvertencia(null, mensaje);
    }

    // =======================
    // 🔹 Mensajes de libros
    // =======================
    public static void mensajeLibroAgregado(String titulo) {
        mostrarInfo("El libro '" + titulo + "' ha sido agregado correctamente.");
    }

    public static void mensajeLibronoguardado(String titulo) {
        mostrarInfo("El libro '" + titulo + "' no ha podido ser guardado.");
    }

    public static void mensajeLibroPrestado(String titulo) {
        mostrarInfo("El libro '" + titulo + "' fue prestado.");
    }

    public static void mensajeLibroDevuelto(String titulo) {
        mostrarInfo("El libro '" + titulo + "' fue devuelto.");
    }

    public static void mensajeLibroYaExiste(String titulo) {
        mostrarError("El libro '" + titulo + "' ya existe en el catálogo.");
    }

    public static void mensajeLibroNoEncontrado(String titulo) {
        mostrarError("No se encontró el libro '" + titulo + "' en el catálogo.");
    }
    public static void mensajeDevolucionNOvalida(String titulo) {
        mostrarError("el libro '" + titulo + " no puede ser devuelto por no estar prestado.");
    }

    public static void mensajeLibroNoDisponible(String titulo) {
        mostrarError("El libro '" + titulo + "' no está disponible.");
    }

    public static void mensajeLibroYaDisponible(String titulo) {
        mostrarAdvertencia("El libro '" + titulo + "' ya está disponible.");
    }

    public static void mensajeCampoVacio(String campo) {
        mostrarError("El campo '" + campo + "' no puede estar vacío.");
    }

    public static void mensajeCatalogoVacio() {
        mostrarInfo("El catálogo está vacío.");
    }
    public static void mensajeError() {
        mostrarInfo("Ocurrio un error");
    }

    public static void mensajeregistroexitoso() {
        mostrarInfo("Registro Exitoso.");
    }

    public static void mensajecamposcompletos() {
        mostrarAdvertencia("Complete todos los campos.");
    }

    public static void mensajeusuarioautenticado() {
        mostrarAdvertencia("No hay usuario autenticado.");
    }

    public static void mensajeusuarioguardado() {
        mostrarInfo("Usuario guardado correctamente en: ");
    };

    public static void mensajecedularepetida(String titulo) {
        mostrarAdvertencia("la cedula ya existe");
    }
    public static void mensajeCantidadInvalida(String titulo) {
        mostrarAdvertencia("debe agregar como minimo un libro");
    }
    public static void ejemplarPrestado(String titulo) {
        mostrarAdvertencia("Ya tienes prestado este libro");
    }

    // =======================
    // 🔹 Validaciones básicas
    // =======================
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

    // =======================
    // 🔹 Autenticación visual
    // =======================
    public static AuthService.Role autenticarConDialog(Frame parent, int maxAttempts) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            attempts++;

            LoginView login = new LoginView(parent);
            login.setVisible(true);

            // Si el usuario seleccionó "Registrar"
            if (login.isRegistroSelected()) {
                RegistroView registro = new RegistroView(login);
                registro.setVisible(true);
                // Después de registro, repetir el bucle para intentar login otra vez
                continue;
            }

            // Si presionó cancelar
            if (!login.isConfirmed()) {
                return AuthService.Role.INVALID;
            }

            // Verificar clave
            String clave = login.getClave();
            AuthService.Role role = AuthService.verificarClave(clave);

            if (role == AuthService.Role.INVALID) {
                mostrarError("Clave inválida. Intenta de nuevo.");
                continue;
            }

            mostrarInfo("Inicio de sesión exitoso.");
            return role;
        }

         mostrarAdvertencia("Se superó el número máximo de intentos.");
        return AuthService.Role.INVALID;
    }

    // =======================
    // 🔹 Autenticación de alto nivel
    // =======================
    public static AuthService.Role autenticarOExit(Frame parent, int maxAttempts) {
        AuthService.Role role = autenticarConDialog(parent, maxAttempts);
        if (role == AuthService.Role.INVALID) {
            return null;
        }
        return role;
    }
}
