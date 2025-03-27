package com.es.API_REST_Ez_Learning.dto;

public class UsuarioLoginDTO {

    private String username;
    private String password;

    public UsuarioLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UsuarioLoginDTO(){}

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
}
