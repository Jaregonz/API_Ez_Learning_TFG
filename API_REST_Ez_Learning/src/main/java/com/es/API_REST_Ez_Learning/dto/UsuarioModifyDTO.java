package com.es.API_REST_Ez_Learning.dto;

public class UsuarioModifyDTO {
    private String username;
    private String correoElectronico;
    private String nombre;
    private String apellidos;
    private String nivel;
    private String rol;
    private String imagenPerfil;

    public UsuarioModifyDTO(String username, String correoElectronico, String nombre, String apellidos, String nivel, String rol, String imagenPerfil) {
        this.username = username;
        this.correoElectronico = correoElectronico;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nivel = nivel;
        this.rol = rol;
        this.imagenPerfil = imagenPerfil;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }
}


