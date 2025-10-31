package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import util.Validacion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Personas {
    private static final String RUTA_JSON = util.FilePaths.getUsuariosPath();
    
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("apellido")
    private String apellido;
    @JsonProperty("cedula")
    private String cedula;
    @JsonProperty("telefono")
    private String telefono;
    @JsonProperty("email")
    private String email;
    @JsonProperty("clave")
    private String clave;

    // Constructores
    public Personas() {
    }

    public Personas(String nombre, String apellido, String cedula, String telefono, String email, String clave) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
        this.clave = clave;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    // 🟩 Guarda la persona en una lista dentro de un único JSON
    public void guardarEnJSON() {
        try {
            File archivo = new File(RUTA_JSON);
            File carpeta = archivo.getParentFile();
            if (!carpeta.exists())
                carpeta.mkdirs();

            ObjectMapper mapper = new ObjectMapper();
            List<Personas> lista;

            // Si el archivo ya existe, cargar los usuarios
            if (archivo.exists()) {
                lista = mapper.readValue(archivo, new TypeReference<List<Personas>>() {
                });
            } else {
                lista = new ArrayList<>();
            }

            // Evitar duplicados por cédula
            boolean existe = lista.stream().anyMatch(p -> p.getCedula().equals(this.cedula));
            if (!existe) {
                lista.add(this);
                mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, lista);
                Validacion.mensajeusuarioguardado();
            } else {
                Validacion.mensajecedularepetida(RUTA_JSON);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Cargar todos los usuarios
    public static List<Personas> cargarTodos() {
        try {
            File archivo = new File(RUTA_JSON);
            if (!archivo.exists())
                return new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(archivo, new TypeReference<List<Personas>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 🔹 Buscar usuario por clave
    public static Personas buscarPorClave(String clave) {
        return cargarTodos().stream()
                .filter(p -> p.getClave().equals(clave))
                .findFirst()
                .orElse(null);
    }
}
