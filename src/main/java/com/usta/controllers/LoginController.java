package com.usta.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.usta.App;
import com.usta.models.usuarios.UsuarioImplDAO;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
        UsuarioImplDAO usuarioDao= new UsuarioImplDAO();

        @FXML
        private TextField usuarioField;
        
        @FXML
        private PasswordField passField;
        
    @FXML
    private void switchToMenu() throws IOException {
        try {
            if (usuarioDao.isUsuario(usuarioField.getText(),passField.getText()))
            App.setRoot("secondary");
        } catch (SQLException e) {
            System.out.println("Datos incorrectos");
            e.printStackTrace();
        }
    }
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    private void switchToUsuario() throws IOException {
        App.setRoot("usuario");
    }
}
