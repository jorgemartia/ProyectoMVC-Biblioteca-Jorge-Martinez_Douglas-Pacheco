package view;

import javax.swing.*;
import java.awt.*;
import controller.EventosRegistro;

public class RegistroView extends JFrame implements InterfazBiblioteca {
    private JTextField tfNombre;
    private JTextField tfApellido;
    private JTextField tfCedula;
    private JTextField tfTelefono;
    private JTextField tfEmail;
    private JPasswordField pfClave;

    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private JButton btnVolver;

    public RegistroView() {
        super("Registro de Usuario - Biblioteca");
        initComponents();
        new EventosRegistro(this); // Vincula los eventos
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Registro de Usuario", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.setOpaque(true);
        header.setBackground(new Color(45, 118, 232));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        // Panel central
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        tfNombre = new JTextField(15);
        tfApellido = new JTextField(15);
        tfCedula = new JTextField(15);
        tfTelefono = new JTextField(15);
        tfEmail = new JTextField(15);
        pfClave = new JPasswordField(15);

        int y = 0;
        addLabelAndField(centerPanel, gbc, y++, "Nombre:", tfNombre);
        addLabelAndField(centerPanel, gbc, y++, "Apellido:", tfApellido);
        addLabelAndField(centerPanel, gbc, y++, "Cédula:", tfCedula);
        addLabelAndField(centerPanel, gbc, y++, "Teléfono:", tfTelefono);
        addLabelAndField(centerPanel, gbc, y++, "Email:", tfEmail);
        addLabelAndField(centerPanel, gbc, y++, "Clave:", pfClave);

        add(centerPanel, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnVolver = new JButton("Volver");
        btnLimpiar = new JButton("Limpiar");
        btnRegistrar = new JButton("Registrar");

        // Estilo botones
        JButton[] botones = {btnVolver, btnLimpiar, btnRegistrar};
        for (JButton b : botones) {
            b.setFont(new Font("SansSerif", Font.BOLD, 12));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
        }

        btnRegistrar.setBackground(new Color(34, 139, 34));
        btnLimpiar.setBackground(new Color(128, 128, 128));
        btnVolver.setBackground(new Color(70, 130, 180));

        bottomPanel.add(btnVolver);
        bottomPanel.add(btnLimpiar);
        bottomPanel.add(btnRegistrar);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, int y, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    // Métodos de acceso (getters para el controlador)
    public String getNombre() { return tfNombre.getText(); }
    public String getApellido() { return tfApellido.getText(); }
    public String getCedula() { return tfCedula.getText(); }
    public String getTelefono() { return tfTelefono.getText(); }
    public String getEmail() { return tfEmail.getText(); }
    public String getClave() { return new String(pfClave.getPassword()); }

    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnVolver() { return btnVolver; }

    public void limpiarCampos() {
        tfNombre.setText("");
        tfApellido.setText("");
        tfCedula.setText("");
        tfTelefono.setText("");
        tfEmail.setText("");
        pfClave.setText("");
    }

    @Override
    public void mostrar() {
        setVisible(true);
    }
}
