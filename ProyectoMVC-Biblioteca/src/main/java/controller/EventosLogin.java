package controller;

import util.Validacion;
import util.AuthService;
import util.SessionManager;
import model.Personas;
import view.BibliotecaView;
import view.InterfazBiblioteca;
import view.LoginView;
import view.ProxyView;
import view.RegistroView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventosLogin implements ActionListener {
    private final LoginView login;

    public EventosLogin(LoginView login) {
        this.login = login;
        this.login.getBtnIngresar().addActionListener(this);
        this.login.getBtnRegistrar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == login.getBtnIngresar()) {
            manejarLogin();
        } else if (src == login.getBtnRegistrar()) {
            abrirRegistro();
        }
    }

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
        
        // ✅ INICIAR SESIÓN EN SESSIONMANAGER
        SessionManager.getInstancia().iniciarSesion(nombreUsuario, rol);
        
        // Mostrar estado de sesión para depuración
        SessionManager.getInstancia().mostrarEstadoSesion();
        
        // Mostrar mensaje de bienvenida
        if (rol == AuthService.Role.ADMIN) {
            Validacion.mostrarInfo("Bienvenido administrador.");
        } else {
            Validacion.mostrarInfo("Bienvenido " + nombreUsuario + ".");
        }
        
        login.dispose();
        abrirBiblioteca(rol == AuthService.Role.ADMIN);
    }

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

    private void abrirRegistro() {
        login.setVisible(false);
        RegistroView registro = new RegistroView();
        registro.setVisible(true);
        registro.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                login.setVisible(true);
            }
        });
    }

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

