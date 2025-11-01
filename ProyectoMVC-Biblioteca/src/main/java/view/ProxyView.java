package view;

import controller.Controlador;
/**
 * Vista Proxy del sistema de biblioteca.
 *
 * Actúa como intermediario entre el controlador y la vista principal,
 * controlando el acceso según el rol del usuario (administrador o usuario normal).
 * Si el usuario es administrador, permite mostrar la pestaña de administración;
 * de lo contrario, restringe el acceso.
 *
 * Su función principal es delegar las operaciones de actualización y visualización
 * al controlador, aplicando un control de permisos antes de iniciar la interfaz.
 */
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


