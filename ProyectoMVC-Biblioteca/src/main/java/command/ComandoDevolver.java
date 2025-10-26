package command;

import model.Catalogo;
import model.Libro;
import util.Validacion;

public class ComandoDevolver implements Comando {
    private final String titulo;

    public ComandoDevolver(String titulo) { this.titulo = titulo; }

    @Override
    public void ejecutar() {
        if (!Validacion.campoNoVacio(titulo, "Título")) return;
        Catalogo catalogo = Catalogo.getInstancia();
        Libro l = catalogo.buscarPorTitulo(titulo);
        if (!Validacion.existeLibro(titulo, l)) return;
        if (l.isDisponible()) {
            Validacion.mensajeLibroYaDisponible(titulo);
            return;
        }
        l.devolver();
        Validacion.mensajeLibroDevuelto(titulo);
    }
}