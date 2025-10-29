package view;

import controller.Controlador;


public class ProxyView implements InterfazBiblioteca {
    private final Controlador controlador;
    private final boolean esAdmin;

    public ProxyView(Controlador controlador, boolean esAdmin) {
        this.controlador = controlador;
        this.esAdmin = esAdmin;
    }

    @Override
    public void mostrar() {
        BibliotecaView vista = controlador.getVista();
        vista.setAdminTabVisible(esAdmin);
        vista.mostrar();
    }
}


