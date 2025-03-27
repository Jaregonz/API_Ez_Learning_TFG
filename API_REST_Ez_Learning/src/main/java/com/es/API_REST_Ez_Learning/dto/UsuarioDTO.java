package com.es.API_REST_Ez_Learning.dto;

public class UsuarioDTO {
        private Long id;
        private String username;
        private String password;
        private String correoElectronico;
        private String nombre;
        private String apellidos;
        private String nivel;
        private String rol;
        private String imagenPerfil;

        public UsuarioDTO(Long id, String username, String password, String correoElectronico, String nombre, String apellidos, String nivel, String rol, String imagenPerfil) {
            this.id = id;
            this.username = username;
            this.password = password;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
}


