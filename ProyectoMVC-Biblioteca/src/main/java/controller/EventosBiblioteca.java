package controller;

import command.Comando;
import command.ComandoDevolver;
import command.ComandoPrestar;
import command.ComandoRegistrar;
import model.Catalogo;
import util.Validacion;
import view.BibliotecaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        }
    }

    private void manejarRegistrar() {
        String titulo = vista.getTituloAdminInput();
        String autor = vista.getAutorAdminInput();
        Comando c = new ComandoRegistrar(titulo, autor);
        c.ejecutar();
    }

    private void manejarPrestar() {
        String titulo = vista.getTituloInput();
        Comando c = new ComandoPrestar(titulo);
        c.ejecutar();
    }

    private void manejarDevolver() {
        String titulo = vista.getTituloInput();
        Comando c = new ComandoDevolver(titulo);
        c.ejecutar();
    }

    private void manejarListar() {
        Catalogo catalogo = controlador.getCatalogo();
        if (catalogo.listarLibros().isEmpty()) {
            Validacion.mensajeCatalogoVacio();
            return;
        }
        // Mostrar catálogo en la vista como tabla
        vista.mostrarCatalogo(catalogo.listarLibros());
    }
}

