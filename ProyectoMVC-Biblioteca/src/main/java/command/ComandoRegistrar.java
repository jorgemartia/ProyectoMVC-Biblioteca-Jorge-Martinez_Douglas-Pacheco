package command;

import model.Catalogo;
import model.Libro;
import util.Validacion;

public class ComandoRegistrar implements Comando {
    private final String titulo;
    private final String autor;

    public ComandoRegistrar(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    @Override
    public void ejecutar() {
        if (!Validacion.campoNoVacio(titulo, "Título") || !Validacion.campoNoVacio(autor, "Autor")) {
            return;
        }

        Catalogo catalogo = Catalogo.getInstancia();
        if (!Validacion.noExisteLibroEnCatalogo(titulo, catalogo)) return;

        Libro libro = new Libro(titulo, autor);
        catalogo.agregarLibro(libro);
        Validacion.mensajeLibroAgregado(titulo);
    }
}
