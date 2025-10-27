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

    // campos para la pestaña de operaciones
    private final JTextField tfTituloOp = new JTextField(20);
    private final JTextField tfAutorOp = new JTextField(20);
    // campos para la pestaña de administración (registro)
    private final JTextField tfTituloAdmin = new JTextField(20);
    private final JTextField tfAutorAdmin = new JTextField(20);

    // botones expuestos para el manejador de eventos
    public final JButton btnRegistrar = new JButton("Registrar");
    public final JButton btnPrestar = new JButton("Prestar");
    public final JButton btnDevolver = new JButton("Devolver");
    public final JButton btnListar = new JButton("Listar catálogo");

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
        JTabbedPane tabs = new JTabbedPane();

        // Panel Operaciones (prestar, devolver, listar)
        JPanel panelOp = new JPanel(new GridBagLayout());
        panelOp.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        GridBagConstraints gbcOp = new GridBagConstraints();
        gbcOp.insets = new Insets(6,6,6,6);
        gbcOp.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTituloOp = new JLabel("Título:");
        lblTituloOp.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbcOp.gridx = 0; gbcOp.gridy = 0; gbcOp.weightx = 0.0;
        panelOp.add(lblTituloOp, gbcOp);
        tfTituloOp.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbcOp.gridx = 1; gbcOp.gridy = 0; gbcOp.weightx = 1.0;
        panelOp.add(tfTituloOp, gbcOp);

        JLabel lblAutorOp = new JLabel("Autor:");
        lblAutorOp.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbcOp.gridx = 0; gbcOp.gridy = 1; gbcOp.weightx = 0.0;
        panelOp.add(lblAutorOp, gbcOp);
        tfAutorOp.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbcOp.gridx = 1; gbcOp.gridy = 1; gbcOp.weightx = 1.0;
        panelOp.add(tfAutorOp, gbcOp);

        JPanel panelBtnsOp = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBtnsOp.setBorder(BorderFactory.createEmptyBorder(8,0,0,0));
        styleButton(btnPrestar);
        styleButton(btnDevolver);
        styleButton(btnListar);
        panelBtnsOp.add(btnPrestar);
        panelBtnsOp.add(btnDevolver);
        panelBtnsOp.add(btnListar);
        gbcOp.gridx = 0; gbcOp.gridy = 2; gbcOp.gridwidth = 2; gbcOp.weightx = 1.0;
        panelOp.add(panelBtnsOp, gbcOp);

        panelAdmin = new JPanel(new GridBagLayout());
        panelAdmin.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        GridBagConstraints gbcAd = new GridBagConstraints();
        gbcAd.insets = new Insets(6,6,6,6);
        gbcAd.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTituloAd = new JLabel("Título:");
        lblTituloAd.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbcAd.gridx = 0; gbcAd.gridy = 0; gbcAd.weightx = 0.0;
        panelAdmin.add(lblTituloAd, gbcAd);
        tfTituloAdmin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbcAd.gridx = 1; gbcAd.gridy = 0; gbcAd.weightx = 1.0;
        panelAdmin.add(tfTituloAdmin, gbcAd);

        JLabel lblAutorAd = new JLabel("Autor:");
        lblAutorAd.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbcAd.gridx = 0; gbcAd.gridy = 1; gbcAd.weightx = 0.0;
        panelAdmin.add(lblAutorAd, gbcAd);
        tfAutorAdmin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbcAd.gridx = 1; gbcAd.gridy = 1; gbcAd.weightx = 1.0;
        panelAdmin.add(tfAutorAdmin, gbcAd);

        // Botón registrar destacado
        styleButton(btnRegistrar);
        btnRegistrar.setBackground(new Color(34,139,34));
        JPanel panelBtnsAd = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBtnsAd.setBorder(BorderFactory.createEmptyBorder(8,0,0,0));
        panelBtnsAd.add(btnRegistrar);
        gbcAd.gridx = 0; gbcAd.gridy = 2; gbcAd.gridwidth = 2; gbcAd.weightx = 1.0;
        panelAdmin.add(panelBtnsAd, gbcAd);

        // Añadir pestañas
        tabs.addTab("Operaciones", panelOp);
        tabs.addTab(ADMIN_TAB_TITLE, panelAdmin);

        add(tabs, BorderLayout.CENTER);

        // Guardar referencia a tabs para controlar visibilidad desde Proxy
        this.tabs = tabs;

        // Ajustes finales
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private JTabbedPane tabs;

    private void styleButton(JButton b) {
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBackground(new Color(70, 130, 180));
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Getters para operaciones (prestar/devolver)
    public String getTituloInput() { return tfTituloOp.getText(); }
    public String getAutorInput() { return tfAutorOp.getText(); }

    // Getters para administración (registro)
    public String getTituloAdminInput() { return tfTituloAdmin.getText(); }
    public String getAutorAdminInput() { return tfAutorAdmin.getText(); }

    // Control de visibilidad de la pestaña de administración
    public void setAdminTabVisible(boolean visible) {
        int index = tabs.indexOfTab(ADMIN_TAB_TITLE);
        if (visible) {
            if (index < 0) {
                tabs.addTab(ADMIN_TAB_TITLE, panelAdmin);
                index = tabs.indexOfTab(ADMIN_TAB_TITLE);
            }
            if (index >= 0) tabs.setSelectedIndex(index);
        } else {
            if (index >= 0) {
                tabs.remove(index);
            }
            if (tabs.getTabCount() > 0) tabs.setSelectedIndex(0);
        }
    }

    // Mostrar catálogo en una tabla dentro de un diálogo modal
    public void mostrarCatalogo(List<Libro> libros) {
        String[] columnas = {"Título", "Autor", "Disponible"};
        Object[][] data = new Object[libros.size()][];
        for (int i = 0; i < libros.size(); i++) {
            Libro l = libros.get(i);
            data[i] = new Object[]{l.getTitulo(), l.getAutor(), l.isDisponible() ? "Sí" : "No"};
        }
        JTable table = new JTable(data, columnas);
        table.setEnabled(false);
        table.setFillsViewportHeight(true);
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