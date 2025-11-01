package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import controller.EventosLogin;
/**
 * Vista de inicio de sesión del sistema de biblioteca.
 *
 * Muestra una ventana de tipo diálogo donde el usuario puede ingresar su clave
 * o elegir registrarse. Sirve como punto de entrada al sistema, 
 * controlando si el usuario accede como administrador o usuario normal.
 *
 * Incluye campos de contraseña y botones para continuar o registrarse, 
 * gestionados por la clase {@code EventosLogin}.
 */
public class LoginView extends JDialog {

    private final JPasswordField txtClave;
    private final JButton IngresarButton;
    private final JButton RegistrarButton;

    private boolean confirmed = false;
    private boolean registroSelected = false;

    public LoginView(Frame parent) {
        super(parent, "Iniciar Sesión", true);
        setSize(500, 450); // Ventana un poco más alta
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 247, 250));
        setLayout(new GridBagLayout());

        // 🎨 Colores base
        Color azul = new Color(25, 118, 210);
        Color grisTexto = new Color(90, 90, 90);
        Color fondoPanel = Color.WHITE;

        // --- Panel principal (tarjeta) ---
        JPanel panelCard = new JPanel(new GridBagLayout());
        panelCard.setBackground(fondoPanel);
        panelCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));
        panelCard.setPreferredSize(new Dimension(380, 360)); // más alto para ver todo

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

        // --- Campo de clave ---
        gbc.gridy = 2;
        JLabel lblClave = new JLabel("Password");
        lblClave.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblClave.setForeground(grisTexto);
        lblClave.setHorizontalAlignment(SwingConstants.LEFT);
        panelCard.add(lblClave, gbc);

        txtClave = new JPasswordField();
        txtClave.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtClave.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1, true),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        txtClave.setPreferredSize(new Dimension(240, 40));

        gbc.gridy = 3;
        panelCard.add(txtClave, gbc);

        // --- Botón Ingresar (Continue) ---
        IngresarButton = new JButton("Continue");
        IngresarButton.setBackground(azul);
        IngresarButton.setForeground(Color.WHITE);
        IngresarButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        IngresarButton.setFocusPainted(false);
        IngresarButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        IngresarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        IngresarButton.setPreferredSize(new Dimension(260, 45));

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelCard.add(IngresarButton, gbc);

        // --- Botón Registrar ---
        RegistrarButton = new JButton("Register");
        RegistrarButton.setBackground(new Color(25, 118, 210)); // mismo color que Continue
        RegistrarButton.setForeground(Color.WHITE);
        RegistrarButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        RegistrarButton.setFocusPainted(false);
        RegistrarButton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        RegistrarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        RegistrarButton.setPreferredSize(new Dimension(260, 45));

        gbc.gridy = 5; // debajo del botón azul
        panelCard.add(RegistrarButton, gbc);

        add(panelCard);

        // --- Enlazar eventos ---
        new EventosLogin(this);
    }

    // --- Métodos de acceso para EventosLogin ---
    public String getClave() {
        return new String(txtClave.getPassword());
    }

    public JButton getIngresarButton() {
        return IngresarButton;
    }

    public JButton getRegistrarButton() {
        return RegistrarButton;
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