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

/**
 * Controla los eventos generados desde la vista principal de la biblioteca.
 * 
 * <p>Implementa {@link ActionListener} para gestionar las acciones de los
 * botones como registrar, prestar, devolver libros o cerrar sesión.</p>
 */

public class EventosBiblioteca implements ActionListener {
    private final Controlador controlador;
    private final BibliotecaView vista;

    public EventosBiblioteca(Controlador controlador, BibliotecaView vista) {
        this.controlador = controlador;
        this.vista = vista;
        registrarListeners();
    }
    /**
     * Registra los listeners de los botones de la vista.
     */
    private void registrarListeners() {
        vista.registrarButton.addActionListener(this);
        vista.prestarButton.addActionListener(this);
        vista.devolverButton.addActionListener(this);
        vista.CerrarSesionButton.addActionListener(this); // Ahora es "Cerrar Sesión"
        vista.LimpiarCatalogoButton.addActionListener(this);
    }
    /**
     * Maneja los eventos de acción generados por la vista.
     * @param e evento disparado por un componente
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == vista.registrarButton) {
            manejarRegistrar();
        } else if (src == vista.prestarButton) {
            manejarPrestar();
        } else if (src == vista.devolverButton) {
            manejarDevolver();
        } else if (src == vista.CerrarSesionButton) {
            manejarCerrarSesion();
        } else if (e.getSource() == vista.LimpiarCatalogoButton) {
            manejarLimpiarCatalogo();
        }
    }
    /**
     * Maneja el registro de un nuevo libro.
     */
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
    /**
     * Maneja el préstamo de un libro.
     */
    private void manejarPrestar() {
        SessionManager session = SessionManager.getInstancia();
        session.mostrarEstadoSesion();

        if (!session.hayUsuarioAutenticado()) {
            Validacion.mensajeusuarioautenticado();
            return;
        }
        String titulo = vista.getTituloInput();
        String autor = vista.getAutorInput();
        Comando c = new ComandoPrestar(titulo, autor);
        c.ejecutar();

        controlador.actualizarTablas();
    }
    /**
     * Maneja la devolución de un libro.
     */
    private void manejarDevolver() {
        SessionManager session = SessionManager.getInstancia();
        session.mostrarEstadoSesion();

        if (!session.hayUsuarioAutenticado()) {
            Validacion.mensajeusuarioautenticado();
            return;
        }

        String titulo = vista.getTituloInput();
        String autor = vista.getAutorInput();
        Comando c = new ComandoDevolver(titulo, autor);
        c.ejecutar();

        controlador.actualizarTablas();
    }
    /**
     * Maneja el cierre de sesión del usuario actual y vueve al login.
     */
    private void manejarCerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas cerrar sesión?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager session = SessionManager.getInstancia();
            String usuario = session.getUsuarioActual();
            session.cerrarSesion();
            
            vista.dispose();

            Validacion.mostrarInfo("¡Hasta pronto " + (usuario != null ? usuario : "usuario") + "!");
            
            abrirLogin();
        }
    }
    /**
     * Abre la vista de login.
     */
    private void abrirLogin() {
        LoginView login = new LoginView(null);
        login.setVisible(true);
    }
    /**
     * Maneja la limpieza completa del catálogo de libros.
     */
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
    /**
     * Limpia completamente el catálogo de libros.
     */

    public void limpiarCatalogo() {
        Catalogo catalogo = Catalogo.getInstancia();
        catalogo.limpiar();
    }
}