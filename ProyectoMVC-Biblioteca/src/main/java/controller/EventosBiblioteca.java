package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import command.Comando;
import command.ComandoAgregar;
import command.ComandoDevolver;
import command.ComandoPrestar;
import model.Catalogo;
import util.SessionManager;
import util.Validacion;
import view.BibliotecaView;
import view.LoginView;

public class EventosBiblioteca implements ActionListener {
    private final Controlador controlador;
    private final BibliotecaView vista;

    public EventosBiblioteca(Controlador controlador, BibliotecaView vista) {
        this.controlador = controlador;
        this.vista = vista;
        registrarListeners();
    }

    private void registrarListeners() {
        vista.btnRegistrar.addActionListener(this);
        vista.btnPrestar.addActionListener(this);
        vista.btnDevolver.addActionListener(this);
        vista.btnCerrarSesion.addActionListener(this); // Ahora es "Cerrar Sesión"
        vista.btnLimpiarCatalogo.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == vista.btnRegistrar) {
            manejarRegistrar();
        } else if (src == vista.btnPrestar) {
            manejarPrestar();
        } else if (src == vista.btnDevolver) {
            manejarDevolver();
        } else if (src == vista.btnCerrarSesion) { // Ahora cierra sesión
            manejarCerrarSesion();
        } else if (e.getSource() == vista.btnLimpiarCatalogo) {
            manejarLimpiarCatalogo();
        }
    }

    private void manejarRegistrar() {
        String titulo = vista.getTituloAdminInput();
        String autor = vista.getAutorAdminInput();
        String isbn = vista.getIsbnAdminInput();
        String categoria = vista.getCategoriaAdminInput();
        int cantidadTotal = vista.getCantidadAdminInput();

        Comando c = new ComandoAgregar(titulo, autor, isbn, categoria, cantidadTotal);
        c.ejecutar();

        controlador.actualizarTablas();
    }

    private void manejarPrestar() {
        SessionManager session = SessionManager.getInstancia();
        session.mostrarEstadoSesion();

        if (!session.hayUsuarioAutenticado()) {
            Validacion.mensajeusuarioautenticado();
            return;
        }
        String titulo = vista.getTituloInput();
        Comando c = new ComandoPrestar(titulo);
        c.ejecutar();

        controlador.actualizarTablas();
    }

    private void manejarDevolver() {
        SessionManager session = SessionManager.getInstancia();
        session.mostrarEstadoSesion();

        if (!session.hayUsuarioAutenticado()) {
            Validacion.mensajeusuarioautenticado();
            return;
        }

        String titulo = vista.getTituloInput();
        Comando c = new ComandoDevolver(titulo);
        c.ejecutar();

        controlador.actualizarTablas();
    }

    private void manejarCerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas cerrar sesión?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Cerrar sesión en SessionManager
            SessionManager session = SessionManager.getInstancia();
            String usuario = session.getUsuarioActual();
            session.cerrarSesion();
            
            // Cerrar la ventana actual
            vista.dispose();
            
            // Mostrar mensaje de despedida
            Validacion.mostrarInfo("¡Hasta pronto " + (usuario != null ? usuario : "usuario") + "!");
            
            // Volver al login
            abrirLogin();
        }
    }

    private void abrirLogin() {
        LoginView login = new LoginView(null);
        login.setVisible(true);
    }

    private void manejarLimpiarCatalogo() {
        int confirm = JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas limpiar completamente el catálogo?",
                "Confirmar limpieza",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            limpiarCatalogo();
            controlador.actualizarTablas();
            Validacion.mensajeCatalogoVacio();
        }
    }

    public void limpiarCatalogo() {
        Catalogo catalogo = Catalogo.getInstancia();
        catalogo.limpiar();
    }
}