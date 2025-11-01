package controller;

import java.util.List;

import model.Catalogo;
import view.InterfazBiblioteca;
/**
 * Controlador principal que coordina la interacción entre la vista y el modelo.
 * 
 * <p>Se encarga de inicializar la interfaz, actualizar las tablas
 * del catálogo y los préstamos, y gestionar la visibilidad
 * de las secciones administrativas.</p>
 */
public class Controlador {
    private final Catalogo catalogo = Catalogo.getInstancia();
    private final InterfazBiblioteca vista;

    public Controlador(InterfazBiblioteca vista) {
        this.vista = vista;
    }
    /**
     * Inicia la aplicación mostrando la interfaz y actualizando las tablas.
     */
    public void iniciar() {
        vista.mostrar();
        actualizarTablas();
    }
    /**
     * Retorna el catálogo actual.
     * @return instancia del catálogo
     */
    public Catalogo getCatalogo() {
        return catalogo;
    }

    /**
     * Actualiza todas las tablas de la vista
     */
    public void actualizarTablas() {
        actualizarTablaCatalogo();
        actualizarTablaPrestamos();
    }

    /**
     * Actualiza la tabla del catálogo
     */
    public void actualizarTablaCatalogo() {
        List<Object[]>  datosCatalogo = catalogo.getCatalogoCompleto();
        vista.actualizarTablaCatalogo(datosCatalogo);
    }

    /**
     * Actualiza la tabla de préstamos
     */
    public void actualizarTablaPrestamos() {
         List<Object[]> datosPrestamos = catalogo.getPrestamosActivos();
        vista.actualizarTablaPrestamos(datosPrestamos);
    }
    /**
     * Cambia la visibilidad de la pestaña de administración.
     * @param visible true para mostrar la pestaña, false para ocultarla
     */
    public void setAdminTabVisible(boolean visible) {
        vista.setAdminTabVisible(visible);
    }

    public void setPrestamosTabVisible(boolean visible) {
    vista.setPrestamosTabVisible(visible);
}
}