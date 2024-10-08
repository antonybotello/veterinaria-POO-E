package com.usta.models.usuarios;

public class Usuario {
    private int id ;
    private String documento;
    private String nombres;
    private String apellidos;
    private String correo;
    private String clave;
    private String foto;

    public Usuario() {
    }
    //crear sin foto
    public Usuario(String documento, String nombres, String apellidos, String correo) {
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.clave= nombres.charAt(0)+apellidos.charAt(0)+documento;
    }
    //crear con foto
    public Usuario(String documento, String nombres, String apellidos, String correo,String foto) {
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.clave= nombres.charAt(0)+apellidos.charAt(0)+documento;
        this.foto=foto;

    }
    //para el listar
    public Usuario(int id, String documento, String nombres, String apellidos, String correo, String clave,String foto) {
        this.id = id;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.clave=clave;
        this.foto=foto;

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
    @Override
    public String toString() {
        return nombres+" "+apellidos;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    

}