package util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Diseno {
    // Colores
    public static final Color COLOR_PRIMARIO = new Color(41, 128, 185);
    public static final Color COLOR_SECUNDARIO = new Color(52, 152, 219);
    public static final Color COLOR_EXITO = new Color(39, 174, 96);
    public static final Color COLOR_PELIGRO = new Color(231, 76, 60);
    public static final Color COLOR_ADVERTENCIA = new Color(243, 156, 18);
    public static final Color COLOR_INFO = new Color(52, 152, 219);
    public static final Color COLOR_CERRAR_SESION = new Color(155, 89, 182);
    public static final Color COLOR_FONDO = new Color(245, 247, 250);
    public static final Color COLOR_BORDE = new Color(220, 220, 220);
    public static final Color COLOR_TEXTO_GRIS = new Color(100, 100, 100);

    // Fuentes
    public static final Font FUENTE_TITULO = new Font("SansSerif", Font.BOLD, 22);
    public static final Font FUENTE_SUBTITULO = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FUENTE_NORMAL = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font FUENTE_NEGRITA = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FUENTE_BOTON = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FUENTE_PIE = new Font("SansSerif", Font.ITALIC, 11);

    // Métodos para crear componentes

    public static JLabel crearLabelTitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(FUENTE_TITULO);
        label.setForeground(Color.WHITE);
        return label;
    }

    public static JLabel crearLabelSubtitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(FUENTE_SUBTITULO);
        label.setForeground(Color.WHITE);
        return label;
    }

    public static JLabel crearLabelNormal(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_NORMAL);
        return label;
    }

    public static JLabel crearLabelTituloPanel(String texto, int cantidad) {
        JLabel label = new JLabel(texto + " (" + cantidad + ")", SwingConstants.CENTER);
        label.setFont(FUENTE_NEGRITA);
        label.setName("tituloPanel");
        return label;
    }

    public static JTextField crearTextField(int columnas) {
        JTextField textField = new JTextField(columnas);
        textField.setFont(FUENTE_NORMAL);
        textField.setBorder(crearBordeTextField());
        return textField;
    }

    public static JPasswordField crearPasswordField(int columnas) {
        JPasswordField passwordField = new JPasswordField(columnas);
        passwordField.setFont(FUENTE_NORMAL);
        passwordField.setBorder(crearBordeTextField());
        return passwordField;
    }

    public static Border crearBordeTextField() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                BorderFactory.createEmptyBorder(5, 8, 5, 8));
    }

    public static JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setFocusPainted(false);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    public static JSpinner crearSpinner(int valor, int min, int max, int paso) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(valor, min, max, paso));
        spinner.setFont(FUENTE_NORMAL);
        return spinner;
    }

    public static JTable crearTabla(Object[][] datos, String[] columnas) {
        DefaultTableModel model = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new JTable(model);
        tabla.setFont(FUENTE_NORMAL);
        tabla.setRowHeight(25);
        tabla.setSelectionBackground(COLOR_SECUNDARIO);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setGridColor(COLOR_BORDE);

        // Centrar contenido de las celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        return tabla;
    }

    public static DefaultTableModel crearModeloTabla(String[] columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public static JScrollPane crearScrollPane(Component componente) {
        JScrollPane scrollPane = new JScrollPane(componente);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
        return scrollPane;
    }

    public static JPanel crearPanelCard() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        return panel;
    }

    public static JPanel crearHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_PRIMARIO);
        header.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return header;
    }

    public static JPanel crearPanelBotones(JButton... botones) {
        JPanel panel = new JPanel();
        for (JButton boton : botones) {
            panel.add(boton);
        }
        return panel;
    }

    public static JPanel crearPanelFormulario(String titulo) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                titulo);
        border.setTitleFont(FUENTE_NEGRITA);
        border.setTitleColor(COLOR_PRIMARIO);
        panel.setBorder(BorderFactory.createCompoundBorder(
                border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        return panel;
    }

    public static GridBagConstraints crearConstraints(int x, int y, int width, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.anchor = anchor;
        return gbc;
    }
    public static GridBagConstraints crearConstraints(int x, int y, int width) {
        return crearConstraints(x, y, width, GridBagConstraints.CENTER);
    }

    public static JLabel crearPiePagina(String texto) {
        JLabel footer = new JLabel(texto, SwingConstants.CENTER);
        footer.setFont(FUENTE_PIE);
        footer.setForeground(COLOR_TEXTO_GRIS);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return footer;
    }

    public static JTabbedPane crearTabbedPane() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(FUENTE_NORMAL);
        return tabs;
    }

    // Métodos para mensajes
    public static void mostrarMensajeInfo(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Biblioteca",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void mostrarMensajeError(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Biblioteca",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarMensajeAdvertencia(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Biblioteca",
                JOptionPane.WARNING_MESSAGE);
    }

    public static int mostrarConfirmacion(Component parent, String mensaje) {
        return JOptionPane.showConfirmDialog(parent, mensaje, "Biblioteca",
                JOptionPane.YES_NO_OPTION);
    }
}