package com.usta.controllers;

import java.io.IOException;

import com.usta.App;
import com.usta.models.usuarios.Usuario;
import com.usta.utils.Session;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WelcomeController {

    @FXML
    private Label welcomeLabel;

    private Usuario usuario;

    public void initialize() {
        // Suponiendo que ya tienes el usuario cargado desde la sesión o base de datos
        this.usuario = Session.getInstance().getCurrentUser(); // Obtenemos el usuario actual de la sesión

        if (usuario != null) {
            welcomeLabel.setText("¡Bienvenido a VetCare, " + usuario.getNombres() + "!");
        } else {
            welcomeLabel.setText("¡Bienvenido a VetCare!");
        }
    }

    @FXML
    private void startApp() {
        // Lógica para cambiar a la siguiente vista o comenzar la aplicación
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