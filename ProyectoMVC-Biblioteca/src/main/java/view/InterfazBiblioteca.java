package view;

public interface InterfazBiblioteca {
    void mostrar();
    void actualizarTablaCatalogo(java.util.List<Object[]> datos);
    void actualizarTablaPrestamos(java.util.List<Object[]> datos);
    void setAdminTabVisible(boolean visible);
}
