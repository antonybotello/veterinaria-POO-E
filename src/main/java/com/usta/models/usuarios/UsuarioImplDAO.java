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
        String query = "INSERT INTO usuarios (documento, nombres, apellidos, correo, rol) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, obj.getDocumento());
            stmt.setString(2, obj.getNombres());
            stmt.setString(3, obj.getApellidos());
            stmt.setString(4, obj.getCorreo());
            stmt.setString(5, obj.getRol());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        Usuario obj= getById(id);
        // String query = "DELETE FROM usuarios WHERE idUsuarios=?";
        // try (PreparedStatement stmt = connection.prepareStatement(query)) {
        //     stmt.setInt(1, id);
        //     stmt.executeUpdate();
        // }
        String query = "UPDATE usuarios SET  estado=? WHERE idUsuarios=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, obj.getEstado());
            stmt.setInt(2, obj.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Usuario> getAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("idUsuarios"),
                        rs.getString("documento"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("correo"),
                        rs.getString("clave"),
                        rs.getString("rol"),
                        rs.getString("estado")
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
                    usuario.setRol(rs.getString("rol"));
                    usuario.setEstado(rs.getString("estado"));
                    return usuario;
                }
            }
        }
        return null;
    }

    @Override
    public void update(Usuario obj) throws SQLException {
        String query = "UPDATE usuarios SET nombres=?, apellidos=?, correo=?, rol=? WHERE idUsuarios=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, obj.getNombres());
            stmt.setString(2, obj.getApellidos());
            stmt.setString(3, obj.getCorreo());
            stmt.setString(4, obj.getRol());
            stmt.setInt(5, obj.getId());
            stmt.executeUpdate();
        }
    }

    public Usuario isUsuario(String doc, String pass) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE documento=? AND clave=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, doc);
            stmt.setString(2, pass);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("idUsuarios"));
                    usuario.setDocumento(rs.getString("documento"));
                    usuario.setNombres(rs.getString("nombres"));
                    usuario.setApellidos(rs.getString("apellidos"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setClave("");
                    usuario.setRol(rs.getString("rol"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en isUsuario: " + e.getMessage());
            throw e;
        }
        return null;
    }
}
