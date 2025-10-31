package view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import util.Diseno;


/**
 * Vista principal del sistema de biblioteca.
 * 
 * Proporciona la interfaz gráfica para gestionar las operaciones de la biblioteca,
 * incluyendo préstamos, devoluciones, registro de libros y visualización del catálogo.
 * 
 * La vista utiliza pestañas (JTabbedPane) para separar las secciones de:
 * - Operaciones de préstamo y devolución.
 * - Catálogo completo de libros.
 * - Historial de préstamos activos.
 * - Administración (visible solo para administradores).
 * 
 * Implementa la interfaz {@code InterfazBiblioteca} para ser controlada por la capa Controlador.
 */

public class BibliotecaView extends JFrame implements InterfazBiblioteca {
    // Componentes de la interfaz
    private final JTextField tfTituloOp = Diseno.crearTextField(20);
    private final JTextField tfAutorOp = Diseno.crearTextField(20);
    private final JTextField tfTituloAdmin = Diseno.crearTextField(20);
    private final JTextField tfAutorAdmin = Diseno.crearTextField(20);
    private final JTextField tfIsbnAdmin = Diseno.crearTextField(20);
    private final JTextField tfCategoriaAdmin = Diseno.crearTextField(20);
    private final JSpinner spCantidadAdmin = Diseno.crearSpinner(1, 1, 1000, 1);

    // Tablas
    private JTable tablaPrestamos;
    private JTable tablaCatalogo;
    private DefaultTableModel modelPrestamos;
    private DefaultTableModel modelCatalogo;

    // Botones
    public final JButton btnRegistrar = Diseno.crearBoton("📗 Registrar Libro", Diseno.COLOR_EXITO);
    public final JButton btnPrestar = Diseno.crearBoton("📘 Prestar", Diseno.COLOR_SECUNDARIO);
    public final JButton btnDevolver = Diseno.crearBoton("📕 Devolver", Diseno.COLOR_PELIGRO);
    public final JButton btnCerrarSesion = Diseno.crearBoton("🚪 Cerrar Sesión", Diseno.COLOR_CERRAR_SESION);
    public final JButton btnLimpiarCatalogo = Diseno.crearBoton("🗑️ Limpiar Catálogo", Diseno.COLOR_PELIGRO);

    private JTabbedPane tabs;
    private JPanel panelAdmin;

    public BibliotecaView() {
        super("Biblioteca");
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Encabezado
        JPanel headerPanel = Diseno.crearHeaderPanel();
        headerPanel.add(Diseno.crearLabelTitulo("📘 Mi Biblioteca"), BorderLayout.NORTH);
        headerPanel.add(Diseno.crearLabelSubtitulo("Sistema de Gestión de Libros"), BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Pestañas
        tabs = Diseno.crearTabbedPane();
        tabs.addTab("Operaciones", crearPanelOperaciones());
        tabs.addTab("Catálogo", crearPanelCatalogo());
        tabs.addTab("Préstamos", crearPanelPrestamos());
        panelAdmin = crearPanelAdministracion();
        tabs.addTab("Administración", panelAdmin);
        
        add(tabs, BorderLayout.CENTER);

        // Pie de página
        add(Diseno.crearPiePagina("Sistema de Biblioteca v2.0 - Haz clic en 'Cerrar Sesión' para salir"), 
            BorderLayout.SOUTH);

        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private JPanel crearPanelOperaciones() {
        JPanel panel = Diseno.crearPanelFormulario("Operaciones de Biblioteca");
        
        panel.add(Diseno.crearLabelNormal("Título del Libro:"), 
                 Diseno.crearConstraints(0, 0, 1));
        panel.add(tfTituloOp, Diseno.crearConstraints(1, 0, 1));
        
        panel.add(Diseno.crearLabelNormal("Autor:"), 
                 Diseno.crearConstraints(0, 1, 1));
        panel.add(tfAutorOp, Diseno.crearConstraints(1, 1, 1));
        
        panel.add(Diseno.crearPanelBotones(btnPrestar, btnDevolver, btnCerrarSesion), 
                 Diseno.crearConstraints(0, 2, 2));
        
        return panel;
    }

    private JPanel crearPanelAdministracion() {
        JPanel panel = Diseno.crearPanelFormulario("Administración de Libros");
        
        agregarCampoFormulario(panel, "Título:", tfTituloAdmin, 0);
        agregarCampoFormulario(panel, "Autor:", tfAutorAdmin, 1);
        agregarCampoFormulario(panel, "ISBN:", tfIsbnAdmin, 2);
        agregarCampoFormulario(panel, "Categoría:", tfCategoriaAdmin, 3);
        
        panel.add(Diseno.crearLabelNormal("Cantidad:"), 
                 Diseno.crearConstraints(0, 4, 1));
        panel.add(spCantidadAdmin, Diseno.crearConstraints(1, 4, 1));
        
        panel.add(Diseno.crearPanelBotones(btnRegistrar, btnLimpiarCatalogo), 
                 Diseno.crearConstraints(0, 5, 2));
        
        return panel;
    }

    private void agregarCampoFormulario(JPanel panel, String label, JTextField field, int fila) {
        panel.add(Diseno.crearLabelNormal(label), Diseno.crearConstraints(0, fila, 1));
        panel.add(field, Diseno.crearConstraints(1, fila, 1));
    }

    private JPanel crearPanelPrestamos() {
        JPanel panel = Diseno.crearPanelCard();
        panel.setLayout(new BorderLayout());

        String[] columnas = {"Usuario", "ID Usuario", "Título Libro", "Autor", "ISBN", "Categoría", "Cantidad Prestada"};
        modelPrestamos = Diseno.crearModeloTabla(columnas);
        tablaPrestamos = Diseno.crearTabla(new Object[][]{}, columnas);
        tablaPrestamos.setModel(modelPrestamos);
        
        JLabel titulo = Diseno.crearLabelTituloPanel("📋 Historial de Préstamos Activos", 0);
        titulo.setName("tituloPrestamos");
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(Diseno.crearScrollPane(tablaPrestamos), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelCatalogo() {
        JPanel panel = Diseno.crearPanelCard();
        panel.setLayout(new BorderLayout());

        String[] columnas = {"Título", "Autor", "ISBN", "Categoría", "Cantidad Total", "Cantidad Disponible", "Disponible"};
        modelCatalogo = Diseno.crearModeloTabla(columnas);
        tablaCatalogo = Diseno.crearTabla(new Object[][]{}, columnas);
        tablaCatalogo.setModel(modelCatalogo);
        
        JLabel titulo = Diseno.crearLabelTituloPanel("📚 Catálogo Completo de Libros", 0);
        titulo.setName("tituloCatalogo");
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(Diseno.crearScrollPane(tablaCatalogo), BorderLayout.CENTER);

        return panel;
    }

    // Getters de entrada
    public String getTituloAdminInput() { return tfTituloAdmin.getText(); }
    public String getAutorAdminInput() { return tfAutorAdmin.getText(); }
    public String getIsbnAdminInput() { return tfIsbnAdmin.getText(); }
    public String getCategoriaAdminInput() { return tfCategoriaAdmin.getText(); }
    public int getCantidadAdminInput() { return (int) spCantidadAdmin.getValue(); }
    public String getTituloInput() { return tfTituloOp.getText(); }
    public String getAutorInput() { return tfAutorOp.getText(); }

    // Implementación de la interfaz
    @Override
    public void setAdminTabVisible(boolean visible) {
        int index = tabs.indexOfTab("Administración");
        if (!visible && index >= 0) {
            tabs.remove(index);
        } else if (visible && index < 0) {
            tabs.addTab("Administración", panelAdmin);
        }
    }

    @Override
    public void actualizarTablaPrestamos(java.util.List<Object[]> datos) {
        modelPrestamos.setRowCount(0);
        for (Object[] prestamo : datos) {
            modelPrestamos.addRow(prestamo);
        }
        actualizarTituloPanel("Préstamos", "📋 Historial de Préstamos Activos", datos.size());
    }

    @Override
    public void actualizarTablaCatalogo(java.util.List<Object[]> datos) {
        modelCatalogo.setRowCount(0);
        for (Object[] libro : datos) {
            modelCatalogo.addRow(libro);
        }
        actualizarTituloPanel("Catálogo", "📚 Catálogo Completo de Libros", datos.size());
    }

    private void actualizarTituloPanel(String nombreTab, String textoBase, int cantidad) {
        int index = tabs.indexOfTab(nombreTab);
        if (index >= 0) {
            JPanel panel = (JPanel) tabs.getComponentAt(index);
            if (panel != null) {
                JLabel titulo = (JLabel) ((BorderLayout) panel.getLayout()).getLayoutComponent(BorderLayout.NORTH);
                if (titulo != null) {
                    titulo.setText(textoBase + " (" + cantidad + ")");
                }
            }
        }
    }

    @Override
    public void mostrar() {
        setVisible(true);
    }
}