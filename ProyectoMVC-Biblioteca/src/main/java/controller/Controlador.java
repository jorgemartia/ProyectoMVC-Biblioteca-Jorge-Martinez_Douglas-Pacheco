package controller;

import java.util.List;

import model.Catalogo;
import view.InterfazBiblioteca;

public class Controlador {
    private final Catalogo catalogo = Catalogo.getInstancia();
    private final InterfazBiblioteca vista;

    public Controlador(InterfazBiblioteca vista) {
        this.vista = vista;
    }

    public void iniciar() {
        vista.mostrar();
        // Actualizar las tablas al iniciar
        actualizarTablas();
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }

    /**
     * ✅ Actualiza todas las tablas de la vista
     */
    public void actualizarTablas() {
        actualizarTablaCatalogo();
        actualizarTablaPrestamos();
    }

    /**
     * ✅ Actualiza la tabla del catálogo
     */
    public void actualizarTablaCatalogo() {
        List<Object[]>  datosCatalogo = catalogo.getCatalogoCompleto();
        vista.actualizarTablaCatalogo(datosCatalogo);
    }

    /**
     * ✅ Actualiza la tabla de préstamos
     */
    public void actualizarTablaPrestamos() {
         List<Object[]> datosPrestamos = catalogo.getPrestamosActivos();
        vista.actualizarTablaPrestamos(datosPrestamos);
    }

    /**
     * ✅ Establece la visibilidad de la pestaña de administración
     */
    public void setAdminTabVisible(boolean visible) {
        vista.setAdminTabVisible(visible);
    }
}