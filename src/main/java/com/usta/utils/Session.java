package com.usta.utils;

import java.io.IOException;

import com.usta.models.usuarios.Usuario;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToolBar;

public class Session {
    private static Session instance;
    private Usuario currentUser;

    private Session() {
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
    }

    public void clearSession() {
        currentUser = null;
    }

    public void loadMenu(ToolBar menuBar) {
        try {
            FXMLLoader loader = new FXMLLoader();
            String userType = currentUser != null ? currentUser.getRol() : "DEFAULT";

            switch (userType) {
                case "ADMIN":
                    loader.setLocation(getClass().getResource("/com/usta/views/menu/AdminMenu.fxml"));
                    break;
                case "USER":
                    loader.setLocation(getClass().getResource("/com/usta/views/menu/UserMenu.fxml"));
                    break;
                default:
                    loader.setLocation(getClass().getResource("/com/usta/views/menu/UserMenu.fxml"));
            }

            ToolBar menu = loader.load();
            menuBar.getItems().setAll(menu.getItems()); // Reemplaza los elementos actuales con los del menú cargado
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
