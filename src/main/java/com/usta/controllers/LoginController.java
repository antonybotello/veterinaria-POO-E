package com.usta.controllers;

import java.io.IOException;

import com.usta.App;
import com.usta.models.usuarios.UsuarioImplDAO;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
        UsuarioImplDAO usuarioDao= new UsuarioImplDAO();

        @FXML
        private TextField usuarioField;
        
        @FXML
        private PassewordField usuarioField;
        
    @FXML
    private void switchToMenu() throws IOException {
        if (usuarioDao.isUsuario())
        App.setRoot("secondary");
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
