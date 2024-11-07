package com.usta.controllers;

import java.io.IOException;

import com.usta.App;

import javafx.fxml.FXML;

public class MenuController {
    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("welcome");
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
