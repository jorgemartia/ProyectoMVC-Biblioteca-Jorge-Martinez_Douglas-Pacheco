/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import javax.swing.SwingUtilities;
import controller.Controlador;
import util.Validacion;
import util.AuthService;
import view.InterfazBiblioteca;
import view.ProxyView;

public class ProyectoMVCBiblioteca {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear controlador principal
            Controlador controlador = new Controlador();

            // Autenticar usuario
            AuthService.Role role = Validacion.autenticarOExit(null, 3);
            boolean esAdmin = (role == AuthService.Role.ADMIN);

            // Crear Proxy y mostrar interfaz
            InterfazBiblioteca proxy = new ProxyView(controlador, esAdmin);
            proxy.mostrar();
        });
    }
}
