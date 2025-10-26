/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import javax.swing.SwingUtilities;

import controller.Controlador;
import view.InterfazBiblioteca;
import view.ProxyView;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jorge
 */
public class ProyectoMVCBiblioteca {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controlador controlador = new Controlador();
            util.AuthService.Role role = util.Validacion.autenticarOExit(null, 3);
            boolean esAdmin = (role == util.AuthService.Role.ADMIN);
            InterfazBiblioteca proxy = new ProxyView(controlador.getVista(), esAdmin);
            proxy.mostrar();
        });
    }
}
