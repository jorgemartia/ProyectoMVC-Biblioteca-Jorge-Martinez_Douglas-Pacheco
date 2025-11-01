package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.EventosRegistro;
import util.Diseno;

/**
 * Vista encargada del registro de nuevos usuarios en el sistema de biblioteca.
 *
 * Muestra un formulario con campos para ingresar los datos personales del
 * usuario
 * (nombre, apellido, cédula, teléfono, correo y clave), además de los botones
 * para
 * registrar, limpiar o volver al login.
 *
 * Se comunica con el controlador {@code EventosRegistro}, que gestiona las
 * acciones
 * de los botones y valida los datos ingresados antes de guardar la información.
 *
 * No maneja tablas ni funciones de administración, ya que su única función
 * es permitir el registro de nuevos usuarios de forma visual y sencilla.
 */
public class RegistroView extends JFrame implements InterfazBiblioteca {
    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField cedulaField;
    private JTextField telefonoField;
    private JTextField emailField;
    private JPasswordField claveField;

    private JButton RegistrarButton;
    private JButton LimpiarButton;
    private JButton VolverButton;

    private LoginView loginView;

    public RegistroView(LoginView owner) {
        super("Registro de Usuario - Biblioteca");
        this.loginView = owner; // Guardar la referencia
        if (owner != null) {
            setLocationRelativeTo(owner);
        }
        initComponents();
        new EventosRegistro(this, loginView); // Crear eventos aquí
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 550); // Aumenté el tamaño para mejor visualización
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Diseno.COLOR_FONDO);
        setLayout(new BorderLayout(10, 10));

        // Encabezado
        JPanel headerPanel = Diseno.crearHeaderPanel();
        JLabel header = Diseno.crearLabelTitulo("Registro de Usuario");
        headerPanel.add(header, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Panel central con formulario
        JPanel centerPanel = Diseno.crearPanelFormulario("Datos del Usuario");
        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Crear campos con tamaño adecuado
        nombreField = crearCampoGrande();
        apellidoField = crearCampoGrande();
        cedulaField = crearCampoGrande();
        telefonoField = crearCampoGrande();
        emailField = crearCampoGrande();
        claveField = crearPasswordFieldGrande();

        // Agregar campos al formulario con mejor distribución
        agregarCampoFormulario(centerPanel, "Nombre:", nombreField, 0, gbc);
        agregarCampoFormulario(centerPanel, "Apellido:", apellidoField, 1, gbc);
        agregarCampoFormulario(centerPanel, "Cédula:", cedulaField, 2, gbc);
        agregarCampoFormulario(centerPanel, "Teléfono:", telefonoField, 3, gbc);
        agregarCampoFormulario(centerPanel, "Email:", emailField, 4, gbc);
        agregarCampoFormulario(centerPanel, "Clave:", claveField, 5, gbc);

        // Panel para centrar el formulario
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(Diseno.COLOR_FONDO);
        formContainer.add(centerPanel);

        add(formContainer, BorderLayout.CENTER);

        // Panel inferior con botones
        VolverButton = Diseno.crearBoton("Volver", Diseno.COLOR_SECUNDARIO);
        LimpiarButton = Diseno.crearBoton("Limpiar", Diseno.COLOR_ADVERTENCIA);
        RegistrarButton = Diseno.crearBoton("Registrar", Diseno.COLOR_EXITO);

        // Establecer tamaño consistente para botones
        Dimension buttonSize = new Dimension(120, 40);
        VolverButton.setPreferredSize(buttonSize);
        LimpiarButton.setPreferredSize(buttonSize);
        RegistrarButton.setPreferredSize(buttonSize);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Diseno.COLOR_FONDO);
        bottomPanel.add(VolverButton);
        bottomPanel.add(LimpiarButton);
        bottomPanel.add(RegistrarButton);

        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JTextField crearCampoGrande() {
        JTextField field = new JTextField();
        field.setFont(Diseno.FUENTE_NORMAL);
        field.setPreferredSize(new Dimension(250, 35));
        field.setMinimumSize(new Dimension(250, 35));
        field.setBorder(Diseno.crearBordeTextField());
        return field;
    }

    private JPasswordField crearPasswordFieldGrande() {
        JPasswordField field = new JPasswordField();
        field.setFont(Diseno.FUENTE_NORMAL);
        field.setPreferredSize(new Dimension(250, 35));
        field.setMinimumSize(new Dimension(250, 35));
        field.setBorder(Diseno.crearBordeTextField());
        return field;
    }

    private void agregarCampoFormulario(JPanel panel, String label, JComponent field, int fila,
            GridBagConstraints gbc) {
        JLabel lbl = Diseno.crearLabelNormal(label);
        lbl.setPreferredSize(new Dimension(80, 25)); // Tamaño fijo para etiquetas

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        gbc.weightx = 0.7;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }

    // Métodos de acceso (getters para el controlador)
    public String getNombre() {
        return nombreField.getText().trim();
    }

    public String getApellido() {
        return apellidoField.getText().trim();
    }

    public String getCedula() {
        return cedulaField.getText().trim();
    }

    public String getTelefono() {
        return telefonoField.getText().trim();
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getClave() {
        return new String(claveField.getPassword());
    }

    public JButton getRegistrarButton() {
        return RegistrarButton;
    }

    public JButton getLimpiarButton() {
        return LimpiarButton;
    }

    public JButton getVolverButton() {
        return VolverButton;
    }

    public void limpiarCampos() {
        nombreField.setText("");
        apellidoField.setText("");
        cedulaField.setText("");
        telefonoField.setText("");
        emailField.setText("");
        claveField.setText("");
    }

    @Override
    public void mostrar() {
        setVisible(true);
    }

    // Implementación de métodos de la interfaz (no utilizados en esta vista)
    @Override
    public void actualizarTablaCatalogo(java.util.List<Object[]> datos) {
        // No aplica para RegistroView
    }

    @Override
    public void actualizarTablaPrestamos(java.util.List<Object[]> datos) {
        // No aplica para RegistroView
    }

    @Override
    public void setAdminTabVisible(boolean visible) {
        // No aplica para RegistroView
    }

    @Override
public void setPrestamosTabVisible(boolean visible) {
    // No aplica para RegistroView
}

    // Método main para probar la vista
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistroView registroView = new RegistroView(null);
            registroView.setVisible(true);
        });
    }
}