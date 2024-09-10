package com.usta.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.usta.App;
import com.usta.models.usuarios.Usuario;
import com.usta.models.usuarios.UsuarioImplDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsuarioController {

    UsuarioImplDAO usuarioDao= new UsuarioImplDAO();

    //########################## para entrada de datos ##############
    @FXML
    private TextField documentoField;
    @FXML
    private TextField nombresField;
    @FXML
    private TextField apellidosField;
    @FXML
    private TextField correoField;
    //########################## fin entrada de datos ##############

    //########################## para la tabla ##############
    @FXML
    private TableView<Usuario> usuariosTable; // le pedimos que asocie el objeto del fxml a esta variable
    @FXML
    private TableColumn<Usuario, String> documentoCol;
    @FXML
    private TableColumn<Usuario, String> nombresCol;
    @FXML
    private TableColumn<Usuario, String> apellidosCol;
    @FXML
    private TableColumn<Usuario, String> correoCol;

    private ObservableList<Usuario> usuariosDataList = FXCollections.observableArrayList();

    //########################## fintabla ##############

    @FXML
    public void initialize(){
        documentoCol.setCellValueFactory(new PropertyValueFactory<>("documento"));
        nombresCol.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        apellidosCol.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        correoCol.setCellValueFactory(new PropertyValueFactory<>("correo"));

    }

    @FXML
    public void agregarUsuario(){
        String documento= documentoField.getText();
        String nombres= nombresField.getText();
        String apellidos= apellidosField.getText();
        String correo= correoField.getText();
    
        Usuario nuevoUsuario= new Usuario(documento, nombres, apellidos, correo);
        try {
            usuarioDao.add(nuevoUsuario);
        } catch (SQLException e) {
            System.err.println("Error al agregar Usuario!");
            e.printStackTrace();
        }
        usuariosDataList.add(nuevoUsuario);
        usuariosTable.setItems(usuariosDataList);
        limpiarCampos();
    }


    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    public void limpiarCampos(){
        documentoField.setText("");
        nombresField.setText("");
        apellidosField.setText("");
        correoField.setText("");
    }
}
