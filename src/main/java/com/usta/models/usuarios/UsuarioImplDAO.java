package com.usta.models.usuarios;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.usta.utils.*;

public class UsuarioImplDAO implements GenericDAO<Usuario> {

    private Connection connection;

    public UsuarioImplDAO() {
        this.connection = ConnectionManager.getInstance().getConnection();
    }

    @Override
    public void add(Usuario obj) throws SQLException {

        String query = "INSERT INTO usuarios (documento,nombres,apellidos,correo)" +
                "VALUES(?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, obj.getDocumento());
            stmt.setString(2, obj.getNombres());
            stmt.setString(3, obj.getApellidos());
            stmt.setString(4, obj.getCorreo());
            stmt.executeUpdate();
        }

    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM usuarios WHERE idUsuarios=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }

    }

    @Override
    public List<Usuario> getAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        // SELECT * FROM Usuarios
        String query = "SELECT * FROM usuarios";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query);) {
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getInt("idUsuarios"),
                        rs.getString("documento"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("correo"),
                        rs.getString("clave"),
                        rs.getString("foto")



                ));
            }

        }

        return usuarios;
    }

    @Override
    public Usuario getById(int id) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE idUsuarios=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("idUsuarios"));
                    usuario.setDocumento(rs.getString("documento"));
                    usuario.setNombres(rs.getString("nombres"));
                    usuario.setApellidos(rs.getString("apellidos"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setClave(rs.getString("clave"));
                    usuario.setFoto(rs.getString("foto"));

                    return usuario;
                }
            }
        }
        return null;
    }

    @Override
    public void update(Usuario obj) throws SQLException {
        String query = "UPDATE Usuarios SET nombres=?, apellidos=?, correo=?,foto=? WHERE idUsuarios=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, obj.getNombres());
            stmt.setString(2, obj.getApellidos());
            stmt.setString(3, obj.getCorreo());
            stmt.setInt(4, obj.getId());
            stmt.setString(5, obj.getFoto());

            stmt.executeUpdate();
        }
    }

   
    public Boolean isUsuario(String doc, String pass) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE documento=? AND clave=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, doc);
            stmt.setString(2, pass);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    

                    return true;
                }
                else{
                    return false;
                }
            }catch (Exception e) {
             System.err.println("################"+ e.getMessage());   // TODO: handle exception
            }
        }
        return false;
    }
}