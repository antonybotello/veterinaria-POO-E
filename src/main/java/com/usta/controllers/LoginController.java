package com.usta.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.usta.App;
import com.usta.models.usuarios.Usuario;
import com.usta.models.usuarios.UsuarioImplDAO;
import com.usta.utils.Session;
import com.usta.utils.Ventana;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginController {
    UsuarioImplDAO usuarioDao = new UsuarioImplDAO();

    @FXML
    private TextField usuarioField;

    @FXML
    private PasswordField passField;

    @FXML
    private void switchToMenu() throws IOException, SQLException {
        Usuario usuario = usuarioDao.isUsuario(usuarioField.getText(), passField.getText());
        if (usuario != null) {
            Session.getInstance().setCurrentUser(usuario);

            App.setRoot("welcome");
        } else {
            new Ventana(
                    "Error de autenticación", 
                    "Usuario o contraseña incorrectos", 
                    "Por favor, verifica tus credenciales e intenta de nuevo.", 
                    AlertType.ERROR 
            );
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