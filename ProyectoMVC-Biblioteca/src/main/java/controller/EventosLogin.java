package controller;

import util.Validacion;
import util.AuthService;
import view.LoginView;
import view.RegistroView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventosLogin implements ActionListener {
    private final LoginView login;

    public EventosLogin(LoginView login) {
        this.login = login;

        // Conectar listeners
        this.login.getBtnIngresar().addActionListener(this);
        this.login.getBtnRegistrar().addActionListener(this);
        this.login.getBtnCancelar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == login.getBtnIngresar()) {
            manejarLogin();
        } else if (src == login.getBtnRegistrar()) {
            abrirRegistro();
        } else if (src == login.getBtnCancelar()) {
            login.dispose();
            System.exit(0);
        }
    }

    private void manejarLogin() {
        String clave = login.getClave();
        if (clave.isEmpty()) {
            Validacion.mensajeCampoVacio("Clave");
            return;
        }

        AuthService.Role rol = AuthService.verificarClave(clave);
        switch (rol) {
            case ADMIN -> {
                Validacion.mostrarInfo("Bienvenido administrador.");
                login.dispose(); // cierra el login
                abrirBiblioteca(true);
            }
            case USUARIO -> {
                Validacion.mostrarInfo("Bienvenido usuario.");
                login.dispose(); // cierra el login
                abrirBiblioteca(false);
            }
            default -> Validacion.mostrarError("Clave incorrecta. Intenta nuevamente.");
        }
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
        // crea el controlador principal
        Controlador controlador = new Controlador();
        view.InterfazBiblioteca proxy = new view.ProxyView(controlador, esAdmin);
        proxy.mostrar();
    }
}
