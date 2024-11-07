package com.usta.controllers;

import java.io.IOException;

import com.usta.App;
import com.usta.models.usuarios.Usuario;
import com.usta.utils.Session;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;

public class WelcomeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private ToolBar MenuBar;
    private Usuario usuario;


    public void initialize() {
        this.usuario = Session.getInstance().getCurrentUser();
        Session.getInstance().loadMenu(MenuBar);
        if (usuario != null) {
            welcomeLabel.setText("¡Bienvenido a VetCare, " + usuario.getRol().toLowerCase() + " " + usuario.getNombres() + "!");
        } else {
            welcomeLabel.setText("¡Bienvenido a VetCare!");
        }
    }

    @FXML
    private void startApp() {
        // Lógica para iniciar la aplicación
    }

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void switchToMascota() throws IOException {
        App.setRoot("mascota");
    }

    @FXML
    private void switchToUsuario() throws IOException {
        App.setRoot("usuario");
    }
}
