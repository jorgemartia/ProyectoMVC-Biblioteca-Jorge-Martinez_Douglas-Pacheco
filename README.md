Sistema de Gestión de Biblioteca - MVC
📚 Descripción del Proyecto
Este es un sistema completo de gestión de biblioteca desarrollado en Java utilizando el patrón de arquitectura MVC (Modelo-Vista-Controlador). La aplicación permite gestionar libros, préstamos, devoluciones y usuarios con diferentes niveles de acceso (administrador y usuario regular).
Funcionalidades Principales

Gestión de Libros: Agregar, registrar y eliminar libros del catálogo
Sistema de Préstamos: Prestar y devolver libros con control de disponibilidad
Autenticación de Usuarios: Sistema de login con roles (admin/usuario)
Registro de Usuarios: Formulario para registrar nuevos usuarios
Persistencia de Datos: Almacenamiento en archivos JSON
Interfaz Gráfica: Interfaz moderna y responsive con Swing

👥 Integrantes
Jorge Martinez Y Douglas Pacheco
💻 Lenguaje y Herramientas

Lenguaje: Java 8+
Interfaz Gráfica: Java Swing
Persistencia: JSON (Jackson y Gson)
Patrones de Diseño: MVC, Singleton, Command, Proxy
Gestión de Dependencias: Maven
Control de Versiones: Git

🏗️ Patrones de Diseño Implementados y su Rol en MVC
1. MVC (Modelo-Vista-Controlador) - Arquitectura Principal
Rol: Estructura base de la aplicación

Modelo: model/ - Gestiona datos y lógica de negocio (Libro, Personas, Catalogo)
Vista: view/ - Interfaz de usuario (BibliotecaView, LoginView, RegistroView)
Controlador: controller/ - Coordina modelo y vista (Controlador, EventosBiblioteca, EventosLogin)

2. Command - Comandos de Ejecución
Rol: Encapsular operaciones como objetos
java// Ubicación: package command;
public interface Comando {
    void ejecutar();
}
Implementaciones:

ComandoAgregar: Agregar libros al catálogo
ComandoPrestar: Gestionar préstamos de libros
ComandoDevolver: Gestionar devoluciones
ComandoRegistrar: Registrar nuevos libros

Ventaja: Desacopla el invocador de la operación, permitiendo colas, logs y operaciones reversibles.
3. Singleton - Acceso Global Controlado
Rol: Garantizar una única instancia de recursos críticos
java// Ubicación: util/SessionManager, model/Catalogo
private Catalogo() {
        this.libros = cargarLibros();
    }

    public static Catalogo getInstancia() {
        if (instancia == null) {
            instancia = new Catalogo();
        }
        return instancia;
    }

public class SessionManager {
    private static SessionManager instancia;
    
    public static SessionManager getInstancia() {
        if (instancia == null) {
            instancia = new SessionManager();
        }
        return instancia;
    }
}
Uso:

SessionManager: Controla el estado de la sesión del usuario
Catalogo: Gestiona el catálogo único de libros

4. Proxy - Control de Acceso
Rol: Intermediario para control de acceso
java// Ubicación: view/ProxyView
public class ProxyView implements InterfazBiblioteca {
    private final Controlador controlador;
    private final boolean esAdmin;

    public ProxyView(Controlador controlador, boolean esAdmin) {
        this.controlador = controlador;
        this.esAdmin = esAdmin;
    }

    @Override
    public void mostrar() {
        controlador.setAdminTabVisible(esAdmin);
        controlador.iniciar();
      }
    }
Función: Controla el acceso a funcionalidades administrativas basado en el rol del usuario.
5. Facade - Simplificación de Interfaces
Rol: Proporcionar interfaz simplificada a subsistemas complejos
java// Ubicación: util/Validacion, util/Diseno
public class Validacion {
    public static boolean campoNoVacio(String valor, String nombreCampo) {
        // Validación unificada
    }
}
Uso: Validacion y Diseno proporcionan interfaces simplificadas para validaciones y diseño UI.
🗂️ Estructura del Proyecto
src/
├── main/
│   ├── controller/     # Controladores (MVC)
│   ├── model/          # Modelos de datos (MVC)
│   ├── view/           # Vistas (MVC)
|   ├── command/        # Patrón Command
│   ├── util/           # Utilidades y servicios
│   └── main/           # Clase principal
└── data/               # Datos persistentes (JSON)
🔄 Flujo de la Aplicación

Inicio: Autenticación mediante LoginView
Autorización: AuthService verifica credenciales y roles
Sesión: SessionManager mantiene el estado del usuario
Interfaz: ProxyView controla el acceso según permisos
Operaciones: Los comandos ejecutan acciones específicas
Persistencia: JsonStorage guarda los cambios en JSON

🎯 Ventajas de la Arquitectura

Separación de Concerns: Cada componente tiene responsabilidad única
Mantenibilidad: Cambios en una capa no afectan las otras
Testabilidad: Componentes desacoplados permiten testing individual
Extensibilidad: Nuevas funcionalidades se integran fácilmente
Reusabilidad: Componentes como Validacion y Diseno son reutilizables
