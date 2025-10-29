package view;

import model.Personas;
import util.Validacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroView extends JFrame implements InterfazBiblioteca {
    private JTextField tfNombre;
    private JTextField tfApellido;
    private JTextField tfCedula;
    private JTextField tfTelefono;
    private JTextField tfEmail;
    private JPasswordField pfClave;
    private JButton btnRegistrar;
    private JButton btnLimpiar;

    public RegistroView() {
        super("Registro de Usuario - Biblioteca");
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
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
        btnRegistrar = new JButton("Registrar");
        btnLimpiar = new JButton("Limpiar");

        // Estilo botones
        for (JButton b : new JButton[]{btnRegistrar, btnLimpiar}) {
            b.setFont(new Font("SansSerif", Font.BOLD, 12));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
        }
        btnRegistrar.setBackground(new Color(34, 139, 34));
        btnLimpiar.setBackground(new Color(128, 128, 128));

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

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

    private void registrarUsuario() {
        if (tfNombre.getText().isEmpty() || tfApellido.getText().isEmpty() ||
            tfCedula.getText().isEmpty() || tfEmail.getText().isEmpty() ||
            pfClave.getPassword().length == 0) {
            Validacion.mensajecamposcompletos();
            return;
        }

        Personas p = new Personas(
                tfNombre.getText(),
                tfApellido.getText(),
                tfCedula.getText(),
                tfTelefono.getText(),
                tfEmail.getText(),
                new String(pfClave.getPassword()
        ));

        p.guardarEnJSON();
        Validacion.mensajeregistroexitoso();
        limpiarCampos();
    }

    private void limpiarCampos() {
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
