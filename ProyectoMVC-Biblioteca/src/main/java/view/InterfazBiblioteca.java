package view;
/**
 * Interfaz que define los métodos principales para la vista de la biblioteca.
 * 
 * Permite mostrar la interfaz gráfica, actualizar las tablas del catálogo y 
 * los préstamos, así como controlar la visibilidad de la pestaña de administración.
 */
public interface InterfazBiblioteca {
    void mostrar();
    void actualizarTablaCatalogo(java.util.List<Object[]> datos);
    void actualizarTablaPrestamos(java.util.List<Object[]> datos);
    void setAdminTabVisible(boolean visible);
    void setPrestamosTabVisible(boolean visible);
}
