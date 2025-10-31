package controller;

import model.Catalogo;
import view.BibliotecaView;

;

public class Controlador {
    private final Catalogo catalogo = Catalogo.getInstancia();
    private final BibliotecaView vista;

    public Controlador() {
        this.vista = new BibliotecaView(this);
        new EventosBiblioteca(this, vista);
    }

    public void iniciar() {
        vista.mostrar();
    }

    public BibliotecaView getVista() {
        return vista;
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }

}