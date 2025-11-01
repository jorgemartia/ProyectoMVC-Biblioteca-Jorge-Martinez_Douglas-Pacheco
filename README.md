# Sistema de Gestión de Biblioteca - MVC

## 📚 Descripción del Proyecto

Este es un sistema completo de gestión de biblioteca desarrollado en Java utilizando el patrón de arquitectura MVC (Modelo-Vista-Controlador). La aplicación permite gestionar libros, préstamos, devoluciones y usuarios con diferentes niveles de acceso (administrador y usuario regular).

### Funcionalidades Principales

- **Gestión de Libros**: Agregar, registrar y eliminar libros del catálogo.
- **Sistema de Préstamos**: Prestar y devolver libros con control de disponibilidad.
- **Autenticación de Usuarios**: Sistema de login con roles (admin/usuario).
- **Registro de Usuarios**: Formulario para registrar nuevos usuarios.
- **Persistencia de Datos**: Almacenamiento en archivos JSON.
- **Interfaz Gráfica**: Interfaz amigable e intuitiva con el usuario.

## 👥 Integrantes

Jorge Martinez y Douglas Pacheco
## 💻 Tecnologías y Herramientas

| Categoría | Tecnología |
|-----------|------------|
| **Lenguaje** | Java 8+ |
| **Interfaz Gráfica** | Java Swing |
| **Persistencia** | JSON (Jackson y Gson) |
| **Patrones de Diseño** | Singleton, Command, Proxy |
| **Gestión de Dependencias** | Maven |
| **Control de Versiones** | Git |

## 🏗️ Patrones de Diseño Implementados

### 1. MVC (Modelo-Vista-Controlador) - Arquitectura Principal

**Rol**: Estructura base de la aplicación

- **Modelo**: `model/` - Gestiona datos y lógica de negocio (`Libro`, `Personas`, `Catalogo`)
- **Vista**: `view/` - Interfaz de usuario (`BibliotecaView`, `LoginView`, `RegistroView`)
- **Controlador**: `controller/` - Coordina modelo y vista (`Controlador`, `EventosBiblioteca`, `EventosLogin`)

### 2. Command - Comandos de Ejecución

**Rol**: Encapsular operaciones como objetos

```java
// Ubicación: package command;
public interface Comando {
    void ejecutar();
}
```

**Implementaciones**:

| Comando | Descripción |
|---------|-------------|
| `ComandoAgregar` | Agregar libros al catálogo |
| `ComandoPrestar` | Gestionar préstamos de libros |
| `ComandoDevolver` | Gestionar devoluciones |
| `ComandoRegistrar` | Registrar nuevos libros |

**Ventaja**: Desacopla el invocador de la operación, permitiendo colas, logs y operaciones reversibles.

### 3. Singleton - Acceso Global Controlado

**Rol**: Garantizar una única instancia de recursos críticos

```java
// Ubicación: util/SessionManager, model/Catalogo
public class SessionManager {
    private static SessionManager instancia;
    
    public static SessionManager getInstancia() {
        if (instancia == null) {
            instancia = new SessionManager();
        }
        return instancia;
    }
}
public class Catalogo {
    private static Catalogo instancia;
    
    public static Catalogo getInstancia() {
        if (instancia == null) {
            instancia = new Catalogo();
        }
        return instancia;
    }
}
```

**Implementaciones**:
- `SessionManager`: Controla el estado de la sesión del usuario
- `Catalogo`: Gestiona el catálogo único de libros

### 4. Proxy - Control de Acceso

**Rol**: Intermediario para control de acceso

```java
// Ubicación: view/ProxyView
public class ProxyView implements InterfazBiblioteca {
    private final Controlador controlador;
    private final boolean esAdmin;
    
    public void setAdminTabVisible(boolean visible) {
        controlador.setAdminTabVisible(visible && esAdmin);
    }
}
```

**Función**: Controla el acceso a funcionalidades administrativas basado en el rol del usuario.

### 5. Facade - Simplificación de Interfaces

**Rol**: Proporcionar interfaz simplificada a subsistemas complejos

```java
// Ubicación: util/Validacion, util/Diseno
public class Validacion {
    public static boolean campoNoVacio(String valor, String nombreCampo) {
        // Validación unificada
        return valor != null && !valor.trim().isEmpty();
    }
}
```

**Uso**: `Validacion` y `Diseno` proporcionan interfaces simplificadas para validaciones y diseño UI.

## 🗂️ Estructura del Proyecto

```
src/
├── main/
│   ├── controller/      # Controladores (MVC)
│   │   ├── Controlador.java
│   │   ├── EventosBiblioteca.java
│   │   └── EventosLogin.java
│   ├── model/          # Modelos de datos (MVC)
│   │   ├── Libro.java
│   │   ├── Personas.java
│   │   └── Catalogo.java
│   ├── view/           # Vistas (MVC)
│   │   ├── BibliotecaView.java
│   │   ├── LoginView.java
│   │   ├── RegistroView.java
│   │   └── ProxyView.java
│   ├── command/        # Patrón Command
│   │   ├── Comando.java
│   │   ├── ComandoAgregar.java
│   │   ├── ComandoPrestar.java
│   │   ├── ComandoDevolver.java
│   │   └── ComandoRegistrar.java
│   ├── util/           # Utilidades y servicios
│   │   ├── SessionManager.java
│   │   ├── AuthService.java
│   │   ├── JsonStorage.java
│   │   ├── Validacion.java
│   │   └── Diseno.java
│   └── main/           # Clase principal
│       └── Main.java
└── data/               # Datos persistentes (JSON)
    ├── libros.json
    └── usuarios.json
```

## 🔄 Flujo de la Aplicación

**Pasos detallados**:

1. **Inicio**: Autenticación mediante `LoginView`
2. **Autorización**: `AuthService` verifica credenciales y roles
3. **Sesión**: `SessionManager` mantiene el estado del usuario
4. **Interfaz**: `ProxyView` controla el acceso según permisos
5. **Operaciones**: Los comandos ejecutan acciones específicas
6. **Persistencia**: `JsonStorage` guarda los cambios en JSON

## 🎯 Ventajas de la Arquitectura

| Ventaja | Descripción |
|---------|-------------|
| **Separación de Concerns** | Cada componente tiene responsabilidad única |
| **Mantenibilidad** | Cambios en una capa no afectan las otras |
| **Testabilidad** | Componentes desacoplados permiten testing individual |
| **Extensibilidad** | Nuevas funcionalidades se integran fácilmente |
| **Reusabilidad** | Componentes como `Validacion` y `Diseno` son reutilizables |

## 🚀 Instalación y Uso

### Requisitos Previos

- Java 8 o superior
- Maven 3.6+
- IDE (Visual Studio Code, IntelliJ IDEA)

### Instalación

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/sistema-biblioteca-mvc.git

# Entrar al directorio
cd sistema-biblioteca-mvc

# Compilar con Maven
mvn clean install

# Ejecutar la aplicación
mvn exec:java
```

### Credenciales por Defecto

| Usuario | Contraseña | Rol |
|---------|------------|-----|
| `admin` | `admin123` | Administrador |
| `usuario` | `registro` | Usuario Regular |

## 📝 Licencia

Este proyecto fue desarrollado con fines académicos para el curso de Patrones de Diseño.

