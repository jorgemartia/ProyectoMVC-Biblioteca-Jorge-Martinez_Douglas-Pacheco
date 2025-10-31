package controller;

import command.Comando;
import command.ComandoAgregar;
import command.ComandoDevolver;
import command.ComandoPrestar;
import model.Catalogo;
import util.SessionManager;
import util.Validacion;
import view.BibliotecaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

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
        vista.btnListar.addActionListener(this);
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
        } else if (src == vista.btnListar) {
            manejarListar();
        } else if (e.getSource() == vista.btnLimpiarCatalogo) {
            manejarLimpiarCatalogo();
        }
    }

    private void manejarRegistrar() {
        String titulo = vista.getTituloAdminInput();
        String autor = vista.getAutorAdminInput();
        String isbn = vista.getIsbnAdminInput();
        String categoria = vista.getCategoriaAdminInput();

        Comando c = new ComandoAgregar(titulo, autor, isbn, categoria);
        c.ejecutar();
        
        controlador.getCatalogo().recargarLibros();
    }

    private void manejarPrestar() {
        // ✅ OBTENER USUARIO DESDE SESSIONMANAGER
        SessionManager session = SessionManager.getInstancia();
        session.mostrarEstadoSesion();
        
        if (!session.hayUsuarioAutenticado()) {
            Validacion.mensajeusuarioautenticado();
            return;
        }
        
        String usuarioActual = session.getUsuarioActual();
        String titulo = vista.getTituloInput();
        Comando c = new ComandoPrestar(titulo, usuarioActual);
        c.ejecutar();
        
        controlador.getCatalogo().recargarLibros();
    }

    private void manejarDevolver() {
        // ✅ OBTENER USUARIO DESDE SESSIONMANAGER
        SessionManager session = SessionManager.getInstancia();

        session.mostrarEstadoSesion();
        
        if (!session.hayUsuarioAutenticado()) {
            Validacion.mensajeusuarioautenticado();
            return;
        }
        
        String usuarioActual = session.getUsuarioActual();
        String titulo = vista.getTituloInput();
        Comando c = new ComandoDevolver(titulo, usuarioActual);
        c.ejecutar();
        
        controlador.getCatalogo().recargarLibros();
    }

    private void manejarListar() {
        controlador.getCatalogo().recargarLibros();
        
        if (controlador.getCatalogo().getLibros().isEmpty()) {
            Validacion.mensajeCatalogoVacio();
            return;
        }
        vista.mostrarCatalogo(controlador.getCatalogo().getLibros());
    }

    private void manejarLimpiarCatalogo() {
        int confirm = JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas limpiar completamente el catálogo?",
                "Confirmar limpieza",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            limpiarCatalogo();
            Validacion.mensajeCatalogoVacio();
        }
    }

    public void limpiarCatalogo() {
        Catalogo catalogo = Catalogo.getInstancia();
        catalogo.limpiar();
    }
}