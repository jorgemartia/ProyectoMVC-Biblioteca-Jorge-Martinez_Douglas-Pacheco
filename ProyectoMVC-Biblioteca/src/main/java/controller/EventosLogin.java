package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Personas;
import util.AuthService;
import util.SessionManager;
import util.Validacion;
import view.BibliotecaView;
import view.InterfazBiblioteca;
import view.LoginView;
import view.ProxyView;
import view.RegistroView;

/**
 * Controlador que gestiona los eventos del formulario de inicio de sesión.
 * Valida la clave ingresada, determina el rol del usuario e inicia la vista correspondiente.
 */
public class EventosLogin implements ActionListener {
    private final LoginView login;

    public EventosLogin(LoginView login) {
        this.login = login;
        this.login.getIngresarButton().addActionListener(this);
        this.login.getRegistrarButton().addActionListener(this);
    }
    /**
     * Escucha las acciones de los botones "Ingresar" y "Registrar".
     * @param e Evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == login.getIngresarButton()) {
            manejarLogin();
        } else if (src == login.getRegistrarButton()) {
            abrirRegistro();
        }
    }
    /**
     * Verifica las credenciales ingresadas e inicia sesión si son válidas.
     */

    private void manejarLogin() {
        String clave = login.getClave();
        if (clave.isEmpty()) {
            Validacion.mensajeCampoVacio("Clave");
            return;
        }

        AuthService.Role rol = AuthService.verificarClave(clave);
        
        if (rol == AuthService.Role.INVALID) {
            Validacion.mostrarError("Clave incorrecta. Intenta nuevamente.");
            return;
        }

        // Obtener el nombre del usuario
        String nombreUsuario = obtenerNombreUsuario(clave, rol);
        
        // INICIAR SESIÓN EN SESSIONMANAGER
        SessionManager.getInstancia().iniciarSesion(nombreUsuario, rol);
        
        // Mostrar estado de sesión para depuración
        SessionManager.getInstancia().mostrarEstadoSesion();
        
        // Mostrar mensaje de bienvenida
        if (rol == AuthService.Role.ADMIN) {
            Validacion.mostrarInfo("Bienvenido administrador.");
        } else {
            Validacion.mostrarInfo("Bienvenido " + nombreUsuario + ".");
        }
        
        login.dispose();;
        abrirBiblioteca(rol == AuthService.Role.ADMIN);
    }
    /**
     * Obtiene el nombre del usuario según la clave y el rol.
     * @param clave Clave de acceso ingresada.
     * @param rol Rol obtenido del AuthService.
     * @return Nombre completo o "Usuario" si no se encuentra.
     */

    private String obtenerNombreUsuario(String clave, AuthService.Role rol) {
        if (rol == AuthService.Role.ADMIN) {
            return "Admin";
        }
        
        Personas usuario = Personas.buscarPorClave(clave);
        if (usuario != null) {
            return usuario.getNombre() + " " + usuario.getApellido();
        }
        
        return "Usuario";
    }
    
    /**
     * Abre la vista de registro y mantiene visible el login al cerrarse.
     */
    private void abrirRegistro() {
        login.setVisible(false);
        RegistroView registro = new RegistroView(login);
        registro.setVisible(true);
        registro.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                login.setVisible(true);
            }
        });
    }
    /**
     * Abre la interfaz principal de la biblioteca.
     * @param esAdmin Indica si el usuario tiene rol de administrador.
     */
    private void abrirBiblioteca(boolean esAdmin) {
    // Crear la vista
    BibliotecaView bibliotecaView = new BibliotecaView();
    
    // Crear el controlador con la vista
    Controlador controlador = new Controlador(bibliotecaView);
    
    // Crear y registrar eventos
    new EventosBiblioteca(controlador, bibliotecaView);
    
    // Crear el proxy view
    InterfazBiblioteca proxy = new ProxyView(controlador, esAdmin);
    
    // Mostrar a través del proxy
    proxy.mostrar();
}
}

