package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
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

    // Botones expuestos para el manejador de eventos
    public final JButton btnRegistrar = new JButton("Registrar");
    public final JButton btnPrestar = new JButton("Prestar");
    public final JButton btnDevolver = new JButton("Devolver");
    public final JButton btnListar = new JButton("Listar catálogo");
    public final JButton btnLimpiarCatalogo = new JButton("Limpiar catálogo");

    private JTabbedPane tabs;

    public BibliotecaView(Controlador controlador) {
        super("Biblioteca");
        this.controlador = controlador;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel("Mi Biblioteca", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setOpaque(true);
        header.setBackground(new Color(45, 118, 232));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(header, BorderLayout.NORTH);

        // Crear pestañas
        tabs = new JTabbedPane();

        // Panel Operaciones
        JPanel panelOp = crearPanelOperaciones();
        panelAdmin = crearPanelAdministracion();

        // Añadir pestañas
        tabs.addTab("Operaciones", panelOp);
        tabs.addTab(ADMIN_TAB_TITLE, panelAdmin);

        add(tabs, BorderLayout.CENTER);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JPanel crearPanelOperaciones() {
        JPanel panelOp = new JPanel(new GridBagLayout());
        panelOp.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
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
        styleButton(btnPrestar);
        styleButton(btnDevolver);
        styleButton(btnListar);
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
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo Título
        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);
        tfTituloAdmin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(tfTituloAdmin, gbc);

        // Campo Autor
        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblAutor, gbc);
        tfAutorAdmin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(tfAutorAdmin, gbc);

        // Campo ISBN
        JLabel lblIsbn = new JLabel("ISBN:");
        lblIsbn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblIsbn, gbc);
        tfIsbnAdmin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(tfIsbnAdmin, gbc);

        // Campo Categoría
        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblCategoria, gbc);
        tfCategoriaAdmin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(tfCategoriaAdmin, gbc);

        // Botón Registrar
        styleButton(btnRegistrar);
        btnRegistrar.setBackground(new Color(34, 139, 34));

        styleButton(btnLimpiarCatalogo);
        btnLimpiarCatalogo.setBackground(new Color(178, 34, 34));
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtns.add(btnRegistrar);
        panelBtns.add(btnLimpiarCatalogo);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(panelBtns, gbc);

        return panel;

    }

    private void styleButton(JButton b) {
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBackground(new Color(70, 130, 180));
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Getters para pestaña Operaciones
    public String getTituloInput() {
        return tfTituloOp.getText();
    }

    public String getAutorInput() {
        return tfAutorOp.getText();
    }

    // Getters para pestaña Administración
    public String getTituloAdminInput() {
        return tfTituloAdmin.getText();
    }

    public String getAutorAdminInput() {
        return tfAutorAdmin.getText();
    }

    public String getIsbnAdminInput() {
        return tfIsbnAdmin.getText();
    }

    public String getCategoriaAdminInput() {
        return tfCategoriaAdmin.getText();
    }

    // Control de visibilidad de la pestaña de administración
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
            data[i] = new Object[] { l.getTitulo(), l.getAutor(), l.isDisponible() ? "Sí" : "No" };
        }

        JTable table = new JTable(data, columnas);
        table.setEnabled(false);
        JScrollPane scroll = new JScrollPane(table);

        JDialog dlg = new JDialog(this, "Catálogo de libros", true);
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
