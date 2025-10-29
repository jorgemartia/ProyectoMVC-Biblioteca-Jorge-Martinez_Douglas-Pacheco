package controller;


import model.Personas;
import util.Validacion;
import view.LoginView;
import view.RegistroView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventosRegistro implements ActionListener {

    private final RegistroView vista;

    public EventosRegistro(RegistroView vista) {
        this.vista = vista;
        registrarListeners();
    }

    private void registrarListeners() {
        vista.getBtnRegistrar().addActionListener(this);
        vista.getBtnLimpiar().addActionListener(this);
        vista.getBtnVolver().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == vista.getBtnRegistrar()) {
            manejarRegistro();
        } else if (src == vista.getBtnLimpiar()) {
            vista.limpiarCampos();
        } else if (src == vista.getBtnVolver()) {
            manejarVolver();
        }
    }

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
                vista.getClave()
        );

        p.guardarEnJSON();
        Validacion.mensajeregistroexitoso();
        vista.limpiarCampos();
    }

    private void manejarVolver() {
        vista.dispose(); // Cierra la ventana de registro
        LoginView login = new LoginView(null);
        login.setVisible(true);
    }
    
}
