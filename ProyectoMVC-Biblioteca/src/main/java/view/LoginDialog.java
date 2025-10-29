package view;

import java.awt.*;
import javax.swing.*;

public class LoginDialog extends JDialog {
    private final JPasswordField pfClave = new JPasswordField(20);
    private boolean confirmed = false;

    public LoginDialog(Frame parent) {
        super(parent, "Inicio de sesión", true);
        initComponents();
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JLabel header = new JLabel("Bienvenido a Mi Biblioteca", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.setOpaque(true);
        header.setBackground(new Color(45, 118, 232));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblClave = new JLabel("Clave:");
        lblClave.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        center.add(lblClave, gbc);

        pfClave.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        center.add(pfClave, gbc);

        JCheckBox cbShow = new JCheckBox("Mostrar clave");
        cbShow.addActionListener(e -> pfClave.setEchoChar(cbShow.isSelected() ? '\0' : '*'));
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0.0;
        center.add(cbShow, gbc);

        panel.add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancel = new JButton("Cancelar");
        JButton btnOk = new JButton("Aceptar");

        btnOk.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });
        btnCancel.addActionListener(e -> {
            confirmed = false;
            pfClave.setText("");
            setVisible(false);
        });

        // Estilo botones
        for (JButton b : new JButton[]{btnCancel, btnOk}) {
            b.setFont(new Font("SansSerif", Font.BOLD, 12));
            b.setBackground(new Color(70, 130, 180));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
        }
        btnCancel.setBackground(new Color(128, 128, 128));
        btnOk.setBackground(new Color(34, 139, 34));

        buttons.add(btnCancel);
        buttons.add(btnOk);
        panel.add(buttons, BorderLayout.SOUTH);

        getContentPane().add(panel);
    }

    public String getClave() { return new String(pfClave.getPassword()); }
    public boolean isConfirmed() { return confirmed; }
}
