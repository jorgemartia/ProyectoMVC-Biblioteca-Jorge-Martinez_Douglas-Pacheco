package model;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Personas {
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

    public Personas(String nombre, String apellido, String cedula, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
    }

    public Personas() {
    }

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

    public void guardarEnJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // Crear directorio si no existe
            File directorio = new File("datos");
            if (!directorio.exists()) {
                directorio.mkdir();
            }
            // Guardar archivo JSON con la cédula como nombre
            mapper.writeValue(new File("datos/" + this.cedula + ".json"), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Personas cargarDeJSON(String cedula) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File("datos/" + cedula + ".json"), Personas.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

