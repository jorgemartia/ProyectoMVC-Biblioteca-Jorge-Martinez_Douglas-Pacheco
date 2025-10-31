package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import controller.Controlador;
import model.Libro;

public class BibliotecaView extends JFrame implements InterfazBiblioteca {
    @SuppressWarnings("unused")
    private final Controlador controlador;
    private JPanel panelAdmin;
    private final String ADMIN_TAB_TITLE = "Administración";

    // Campos para la pestaña de operaciones
    private final JTextField tfTituloOp = new JTextField(20);
    private final JTextField tfAutorOp = new JTextField(20);

    // Campos para la pestaña de administración (registro)
    private final JTextField tfTituloAdmin = new JTextField(20);
    private final JTextField tfAutorAdmin = new JTextField(20);
    private final JTextField tfIsbnAdmin = new JTextField(20);
    private final JTextField tfCategoriaAdmin = new JTextField(20);
    private final JSpinner spCantidadAdmin = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));

    // Botones expuestos para el manejador de eventos
    public final JButton btnRegistrar = new JButton("📗 Registrar Libro");
    public final JButton btnPrestar = new JButton("📘 Prestar");
    public final JButton btnDevolver = new JButton("📕 Devolver");
    public final JButton btnListar = new JButton("📚 Listar catálogo");
    public final JButton btnLimpiarCatalogo = new JButton("🗑️ Limpiar Catálogo");

    private JTabbedPane tabs;

    public BibliotecaView(Controlador controlador) {
        super("Biblioteca");
        this.controlador = controlador;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Encabezado principal estilo “Mi Biblioteca”
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel headerTitle = new JLabel("📘 Mi Biblioteca", SwingConstants.CENTER);
        headerTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerTitle.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Sistema de Gestión de Libros", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);

        headerPanel.add(headerTitle, BorderLayout.NORTH);
        headerPanel.add(subtitle, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Crear pestañas
        tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Panel Operaciones
        JPanel panelOp = crearPanelOperaciones();
        panelAdmin = crearPanelAdministracion();

        // Añadir pestañas
        tabs.addTab("Operaciones", panelOp);
        tabs.addTab(ADMIN_TAB_TITLE, panelAdmin);

        add(tabs, BorderLayout.CENTER);

        // Pie de página
        JLabel footer = new JLabel("Sistema de Biblioteca v2.0", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 11));
        footer.setForeground(new Color(100, 100, 100));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footer, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JPanel crearPanelOperaciones() {
        JPanel panelOp = new JPanel(new GridBagLayout());
        panelOp.setBorder(BorderFactory.createEmptyBorder());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelOp.add(lblTitulo, gbc);
        tfTituloOp.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelOp.add(tfTituloOp, gbc);

        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelOp.add(lblAutor, gbc);
        tfAutorOp.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelOp.add(tfAutorOp, gbc);

        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        styleButton(btnPrestar, new Color(52, 152, 219));
        styleButton(btnDevolver, new Color(231, 76, 60));
        styleButton(btnListar, new Color(39, 174, 96));
        panelBtns.add(btnPrestar);
        panelBtns.add(btnDevolver);
        panelBtns.add(btnListar);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelOp.add(panelBtns, gbc);

        return panelOp;
    }

    private JPanel crearPanelAdministracion() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo Título
        addLabelField(panel, gbc, 0, "Título:", tfTituloAdmin);
        addLabelField(panel, gbc, 1, "Autor:", tfAutorAdmin);
        addLabelField(panel, gbc, 2, "ISBN:", tfIsbnAdmin);
        addLabelField(panel, gbc, 3, "Categoría:", tfCategoriaAdmin);

        // Campo Cantidad
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblCantidad, gbc);
        spCantidadAdmin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(spCantidadAdmin, gbc);

        // Botones
        styleButton(btnRegistrar, new Color(46, 204, 113));
        styleButton(btnLimpiarCatalogo, new Color(231, 76, 60));
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBtns.add(btnRegistrar);
        panelBtns.add(btnLimpiarCatalogo);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(panelBtns, gbc);

        return panel;
    }

    private void addLabelField(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lbl, gbc);

        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(field, gbc);
    }

    private void styleButton(JButton b, Color color) {
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Getters de entrada
    public String getTituloAdminInput() { return tfTituloAdmin.getText(); }
    public String getAutorAdminInput() { return tfAutorAdmin.getText(); }
    public String getIsbnAdminInput() { return tfIsbnAdmin.getText(); }
    public String getCategoriaAdminInput() { return tfCategoriaAdmin.getText(); }
    public int getCantidadAdminInput() { return (int) spCantidadAdmin.getValue(); }

    public String getTituloInput() { return tfTituloOp.getText(); }
    public String getAutorInput() { return tfAutorOp.getText(); }

    // Control de visibilidad de pestaña
    public void setAdminTabVisible(boolean visible) {
        int index = tabs.indexOfTab(ADMIN_TAB_TITLE);
        if (visible) {
            if (index < 0) {
                tabs.addTab(ADMIN_TAB_TITLE, panelAdmin);
                index = tabs.indexOfTab(ADMIN_TAB_TITLE);
            }
            tabs.setSelectedIndex(index);
        } else if (index >= 0) {
            tabs.remove(index);
            if (tabs.getTabCount() > 0)
                tabs.setSelectedIndex(0);
        }
    }

    // Mostrar catálogo
    public void mostrarCatalogo(List<Libro> libros) {
        String[] columnas = { "Título", "Autor", "Disponible" };
        Object[][] data = new Object[libros.size()][];
        for (int i = 0; i < libros.size(); i++) {
            Libro l = libros.get(i);
            data[i] = new Object[]{ l.getTitulo(), l.getAutor(), l.isDisponible() ? "Sí" : "No" };
        }

        JTable table = new JTable(data, columnas);
        table.setEnabled(false);
        JScrollPane scroll = new JScrollPane(table);

        JDialog dlg = new JDialog(this, "Catálogo de Libros", true);
        dlg.getContentPane().add(scroll);
        dlg.setSize(600, 300);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    @Override
    public void mostrar() {
        setVisible(true);
    }
}
