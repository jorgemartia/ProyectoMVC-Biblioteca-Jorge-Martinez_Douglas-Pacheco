package view;

import javax.swing.*;
import java.awt.*;
import controller.EventosLogin;

public class LoginView extends JDialog {

    private final JPasswordField txtClave;
    private final JButton btnIngresar;
    private final JButton btnRegistrar;
    private final JButton btnCancelar;

    private boolean confirmed = false;
    private boolean registroSelected = false;

    public LoginView(Frame parent) {
        super(parent, "Iniciar Sesión", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 247, 250));
        setLayout(new GridBagLayout());

        // 🎨 Colores base
        Color azul = new Color(25, 118, 210);
        Color grisTexto = new Color(90, 90, 90);
        Color fondoPanel = Color.WHITE;

        // --- Panel principal estilo tarjeta ---
        JPanel panelCard = new JPanel(new GridBagLayout());
        panelCard.setBackground(fondoPanel);
        panelCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));
        panelCard.setPreferredSize(new Dimension(380, 280));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // --- Título ---
        JLabel lblTitulo = new JLabel("Login", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(azul);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelCard.add(lblTitulo, gbc);

        // --- Subtítulo ---
        JLabel lblSub = new JLabel("to get started", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSub.setForeground(grisTexto);

        gbc.gridy = 1;
        panelCard.add(lblSub, gbc);

        // --- Espacio visual ---
        gbc.gridy = 2;
        panelCard.add(Box.createVerticalStrut(10), gbc);

        // --- Campo de clave ---
        JLabel lblClave = new JLabel("Password");
        lblClave.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblClave.setForeground(grisTexto);
        lblClave.setHorizontalAlignment(SwingConstants.LEFT);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelCard.add(lblClave, gbc);

        txtClave = new JPasswordField();
        txtClave.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtClave.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1, true),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtClave.setPreferredSize(new Dimension(240, 40));

        gbc.gridy = 4;
        panelCard.add(txtClave, gbc);

        // --- Espacio ---
        gbc.gridy = 5;
        panelCard.add(Box.createVerticalStrut(15), gbc);

        // --- Botón Ingresar (Continue) ---
        btnIngresar = new JButton("Continue");
        btnIngresar.setBackground(azul);
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setPreferredSize(new Dimension(260, 45));

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panelCard.add(btnIngresar, gbc);

        // --- Espacio ---
        gbc.gridy = 7;
        panelCard.add(Box.createVerticalStrut(10), gbc);

        // --- Enlace de registro ---
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panelInferior.setBackground(fondoPanel);

        JLabel lblNuevo = new JLabel("New User?");
        lblNuevo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNuevo.setForeground(grisTexto);

        btnRegistrar = new JButton("Register");
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRegistrar.setForeground(azul);
        btnRegistrar.setBackground(fondoPanel);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelInferior.add(lblNuevo);
        panelInferior.add(btnRegistrar);

        gbc.gridy = 8;
        panelCard.add(panelInferior, gbc);

        // --- Botón Cancelar (oculto, solo para eventos) ---
        btnCancelar = new JButton();
        btnCancelar.setVisible(false);

        add(panelCard);

        // --- Enlazar eventos ---
        new EventosLogin(this);
    }

    // --- Métodos de acceso para EventosLogin ---
    public String getClave() {
        return new String(txtClave.getPassword());
    }

    public JButton getBtnIngresar() {
        return btnIngresar;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public boolean isRegistroSelected() {
        return registroSelected;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setRegistroSelected(boolean registroSelected) {
        this.registroSelected = registroSelected;
    }
}
