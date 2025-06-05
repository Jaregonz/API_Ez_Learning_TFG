package com.es.API_REST_Ez_Learning.dto;

public class UsuarioModifyDTO {
    private String correoElectronico;
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private String nivel;

    public UsuarioModifyDTO(String correoElectronico, String nombre, String apellidos, String fechaNacimiento, String nivel) {
        this.correoElectronico = correoElectronico;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.nivel = nivel;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getNivel() {
        return nivel;
    }
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}


