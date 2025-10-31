package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Personas;
import util.Validacion;
import view.LoginView;
import view.RegistroView;

/**
 * Controlador que maneja los eventos del formulario de registro.
 * Gestiona el registro de nuevos usuarios, limpieza de campos y retorno al
 * login.
 */
public class EventosRegistro implements ActionListener {

    private final RegistroView vista;
    private final LoginView loginView;

    /**
     * Inicializa el controlador y registra los listeners de los botones.
     * 
     * @param vista Vista del formulario de registro.
     */
    public EventosRegistro(RegistroView vista, LoginView loginView) {
        this.vista = vista;
        this.loginView = loginView;
        registrarListeners();
    }

    /**
     * Asigna los listeners a los botones de la vista.
     */
    private void registrarListeners() {
        vista.getRegistrarButton().addActionListener(this);
        vista.getLimpiarButton().addActionListener(this);
        vista.getVolverButton().addActionListener(this);
    }

    /**
     * Maneja las acciones de los botones según el evento generado.
     * 
     * @param e Evento de acción detectado.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vista.getRegistrarButton()) {
            manejarRegistro();
        } else if (src == vista.getLimpiarButton()) {
            vista.limpiarCampos();
        } else if (src == vista.getVolverButton()) {
            manejarVolver();
        }
    }

    /**
     * Valida los campos del formulario y registra un nuevo usuario si son
     * correctos.
     */
    private void manejarRegistro() {
        if (vista.getNombre().isEmpty() || vista.getApellido().isEmpty() ||
                vista.getCedula().isEmpty() || vista.getEmail().isEmpty() ||
                vista.getClave().isEmpty()) {
            Validacion.mensajecamposcompletos();
            return;
        }

        Personas p = new Personas(
                vista.getNombre(),
                vista.getApellido(),
                vista.getCedula(),
                vista.getTelefono(),
                vista.getEmail(),
                vista.getClave());

        p.guardarEnJSON();
        Validacion.mensajeregistroexitoso();
        vista.limpiarCampos();
    }

    /**
     * Cierra la vista actual y vuelve al formulario de inicio de sesión.
     */

    private void manejarVolver() {
       vista.dispose();
        loginView.setVisible(true);
        loginView.toFront();
        }

    }
