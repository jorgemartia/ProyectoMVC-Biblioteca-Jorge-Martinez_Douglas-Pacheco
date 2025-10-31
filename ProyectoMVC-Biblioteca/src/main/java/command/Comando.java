package command;

/**
 * interfaz comando que define el metodo de ejcutar para los comandos(patrón command).
 * implementa una accion concreta(agregar, prestar, devolver, registrar))
 */

public interface Comando {
    void ejecutar();
}
