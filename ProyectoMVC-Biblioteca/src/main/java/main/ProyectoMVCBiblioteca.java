/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import javax.swing.SwingUtilities;

import controller.Controlador;
import util.AuthService;
import util.Validacion;
import view.BibliotecaView;
import view.ProxyView;
/**
 * Clase principal del proyecto Biblioteca MVC.
 * Inicia la aplicación, gestiona la autenticación y carga la vista principal.
 */
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

