package com.es.API_REST_Ez_Learning.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false, name = "correo_electronico")
    private String correoElectronico;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private Date fechaNacimiento;

    @Column(nullable = true)
    private String nivel;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Usuario> alumnos;

    @ManyToOne
    @JoinColumn(name = "id_profesor", nullable = true)
    private Usuario profesor;

    @Column(nullable = false)
    private String rol;

    @Column(name = "imagen_perfil")
    private String imagenPerfil;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Test> tests;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<EntregaExamen> entregasExamen;

    public Usuario(String username, String correoElectronico, String password, String nombre, String apellidos, String nivel, String rol, String imagenPerfil, Usuario profesor, Date fechaNacimiento) {
        this.username = username;
        this.correoElectronico = correoElectronico;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nivel = nivel;
        this.rol = rol;
        this.imagenPerfil = imagenPerfil;
        this.profesor = profesor;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Usuario> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Usuario> alumnos) {
        this.alumnos = alumnos;
    }

    public Usuario getProfesor() {
        return profesor;
    }

    public void setProfesor(Usuario profesor) {
        this.profesor = profesor;
    }
}
