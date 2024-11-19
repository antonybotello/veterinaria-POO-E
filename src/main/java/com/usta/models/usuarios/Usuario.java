package com.usta.models.usuarios;

public class Usuario {
    private int id;
    private String documento;
    private String nombres;
    private String apellidos;
    private String correo;
    private String clave;
    private String rol;
    private String estado;

    public Usuario() {
    }

    // This particular constructor in the `Usuario` class is a parameterized constructor that
    // initializes a `Usuario` object with the provided values for `documento`, `nombres`, `apellidos`,
    // `correo`, and `rol`.
    public Usuario(String documento, String nombres, String apellidos, String correo, String rol) {
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.clave = nombres.charAt(0) + apellidos.charAt(0) + documento;
        this.rol = rol;
        this.estado= "ACTIVO";
    }

    public Usuario(int id, String documento, String nombres, String apellidos, String correo, String clave, String rol, String estado) {
        this.id = id;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
        this.estado= estado;
    }
    public Usuario(int id, String documento, String nombres, String apellidos, String correo, String clave, String rol) {
        this.id = id;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
