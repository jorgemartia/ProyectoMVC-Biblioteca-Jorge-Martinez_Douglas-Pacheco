/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import javax.swing.SwingUtilities;
import controller.Controlador;
import util.Validacion;
import util.AuthService;
import view.BibliotecaView;
import view.ProxyView;

public class ProyectoMVCBiblioteca {

    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            // Autenticar usuario
            AuthService.Role role = Validacion.autenticarOExit(null, 3);
            if (role == null)
                return;
            boolean esAdmin = (role == AuthService.Role.ADMIN);

            // Crear vista
            BibliotecaView bibliotecaView = new BibliotecaView();
            
            // Crear controlador
            Controlador controlador = new Controlador(bibliotecaView);
            
            // Crear y registrar eventos
            new controller.EventosBiblioteca(controlador, bibliotecaView);
            
            // Crear Proxy y mostrar interfaz
            view.InterfazBiblioteca proxy = new ProxyView(controlador, esAdmin);
            proxy.mostrar();
        });
    }}

