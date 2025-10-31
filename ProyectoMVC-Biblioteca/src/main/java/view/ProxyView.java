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
        controlador.setAdminTabVisible(esAdmin);
        controlador.iniciar();
    }

    @Override
    public void actualizarTablaCatalogo(java.util.List<Object[]> datos) {
        // El proxy delega al controlador
        controlador.actualizarTablaCatalogo();
    }

    @Override
    public void actualizarTablaPrestamos(java.util.List<Object[]> datos) {
        // El proxy delega al controlador
        controlador.actualizarTablaPrestamos();
    }

    @Override
    public void setAdminTabVisible(boolean visible) {
        controlador.setAdminTabVisible(visible);
    }
}


