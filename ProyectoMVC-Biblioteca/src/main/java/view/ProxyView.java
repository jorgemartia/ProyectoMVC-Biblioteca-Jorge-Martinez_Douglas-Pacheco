package view;


public class ProxyView implements InterfazBiblioteca {
    private final BibliotecaView vistaReal;
    private final boolean esAdmin;

    public ProxyView(BibliotecaView vistaReal, boolean esAdmin) {
        this.vistaReal = vistaReal;
        this.esAdmin = esAdmin;
    }

    @Override
    public void mostrar() {
        // Configura los controles visibles/habilitados según permisos
        // Solo los administradores pueden registrar libros
        // Mostrar/ocultar pestaña de administración
        vistaReal.setAdminTabVisible(esAdmin);
        // Deshabilitar el botón registrar si no es admin (defensa en profundidad)
        vistaReal.btnRegistrar.setEnabled(esAdmin);
        if (!esAdmin) {
            vistaReal.btnRegistrar.setToolTipText("Solo administradores pueden registrar libros");
        } else {
            vistaReal.btnRegistrar.setToolTipText(null);
        }

        // Mostrar la vista real en todos los casos (usuarios no-admin podrán prestar, devolver y listar)
        vistaReal.mostrar();
    }
}
