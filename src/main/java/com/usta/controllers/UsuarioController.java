package com.usta.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.usta.App;
import com.usta.models.usuarios.Usuario;
import com.usta.models.usuarios.UsuarioImplDAO;
import com.usta.utils.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsuarioController {

    UsuarioImplDAO usuarioDao = new UsuarioImplDAO();

    @FXML
    private Button agregarBtn;
    @FXML
    private Button editarBtn;
    @FXML
    private Button eliminarBtn;
    @FXML
    private Button cancelarBtn;

    @FXML
    private TextField documentoField;
    @FXML
    private TextField nombresField;
    @FXML
    private TextField apellidosField;
    @FXML
    private TextField correoField;
    @FXML
    private ComboBox<String> CbxRol; // ComboBox para el rol

    @FXML
    private TableView<Usuario> usuariosTable;
    @FXML
    private TableColumn<Usuario, String> documentoCol;
    @FXML
    private TableColumn<Usuario, String> nombresCol;
    @FXML
    private TableColumn<Usuario, String> apellidosCol;
    @FXML
    private TableColumn<Usuario, String> correoCol;
    @FXML
    private TableColumn<Usuario, String> rolCol;

    private ObservableList<Usuario> usuariosDataList = FXCollections.observableArrayList();

    @FXML
    private TextField buscarField;
    @FXML
    private CheckBox documentoChk;
    @FXML
    private CheckBox nombresChk;
    @FXML
    private CheckBox apellidosChk;
    @FXML
    private CheckBox correoChk;
    private FilteredList<Usuario> usuariosFiltrados;

    @FXML
    private Label urlLbl;

    @FXML
    private ToolBar MenuBar;
    @FXML
    public void initialize() {
        Session.getInstance().loadMenu(MenuBar);
        documentoCol.setCellValueFactory(new PropertyValueFactory<>("documento"));
        nombresCol.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        apellidosCol.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        correoCol.setCellValueFactory(new PropertyValueFactory<>("correo"));
        rolCol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        CbxRol.setItems(FXCollections.observableArrayList("ADMIN", "USER", "GUEST"));

        try {
            usuariosDataList.addAll(usuarioDao.getAll());
            usuariosTable.setItems(usuariosDataList);
        } catch (SQLException e) {
            System.err.println("Error al agregar Usuario!");
            e.printStackTrace();
        }
    }

    @FXML
    private void filtrarUsuarios() {
        String filtroTxt = buscarField.getText().toLowerCase();
        if (usuariosFiltrados == null) {
            usuariosFiltrados = new FilteredList<>(usuariosDataList, p -> true);
        }
        usuariosFiltrados.setPredicate(usuario -> {
            if (filtroTxt == null || filtroTxt.isEmpty()) {
                return true;
            }
            boolean documentoMatch = documentoChk.isSelected() && usuario.getDocumento().toLowerCase().contains(filtroTxt);
            boolean nombresMatch = nombresChk.isSelected() && usuario.getNombres().toLowerCase().contains(filtroTxt);
            boolean apellidosMatch = apellidosChk.isSelected() && usuario.getApellidos().toLowerCase().contains(filtroTxt);
            boolean correoMatch = correoChk.isSelected() && usuario.getCorreo().toLowerCase().contains(filtroTxt);
            return documentoMatch || nombresMatch || apellidosMatch || correoMatch;
        });
        usuariosTable.setItems(usuariosFiltrados);
    }

    @FXML
    public void agregarUsuario() {
        String documento = documentoField.getText();
        String nombres = nombresField.getText();
        String apellidos = apellidosField.getText();
        String correo = correoField.getText();
        String rol = CbxRol.getValue();
        Usuario nuevoUsuario = new Usuario(documento, nombres, apellidos, correo, rol);
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
    public void editarUsuario() {
        Usuario usuario = usuariosTable.getSelectionModel().getSelectedItem();
        String documento = documentoField.getText();
        String nombres = nombresField.getText();
        String apellidos = apellidosField.getText();
        String correo = correoField.getText();
        String clave = usuario.getClave();
        String rol = CbxRol.getValue();
        Usuario usuarioEditado = new Usuario(usuario.getId(), documento, nombres, apellidos, correo, clave, rol);
        try {
            usuarioDao.update(usuarioEditado);
            usuariosDataList.remove(usuariosTable.getSelectionModel().getSelectedItem());
            usuariosDataList.add(usuarioEditado);
        } catch (SQLException e) {
            System.err.println("Error al editar Usuario!");
            e.printStackTrace();
        }
        usuariosTable.setItems(usuariosDataList);
        limpiarCampos();
    }

    @FXML
    public void eliminarUsuario() {
        Usuario usuario = usuariosTable.getSelectionModel().getSelectedItem();
        try {
            usuarioDao.delete(usuario.getId());
            usuariosDataList.remove(usuario);
        } catch (SQLException e) {
            System.err.println("Error al eliminar Usuario!");
            e.printStackTrace();
        }
        usuariosTable.setItems(usuariosDataList);
        limpiarCampos();
    }

    @FXML
    public void seleccionarUsuario() {
        Usuario usuario = usuariosTable.getSelectionModel().getSelectedItem();
        documentoField.setText(usuario.getDocumento());
        documentoField.setEditable(false);
        nombresField.setText(usuario.getNombres());
        apellidosField.setText(usuario.getApellidos());
        correoField.setText(usuario.getCorreo());
        CbxRol.setValue(usuario.getRol());
        agregarBtn.setVisible(false);
        editarBtn.setVisible(true);
        eliminarBtn.setVisible(true);
        cancelarBtn.setVisible(true);
    }

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

    @FXML
    public void limpiarCampos() {
        documentoField.setText("");
        documentoField.setEditable(true);
        nombresField.setText("");
        apellidosField.setText("");
        correoField.setText("");
        CbxRol.setValue(null);
        agregarBtn.setVisible(true);
        editarBtn.setVisible(false);
        eliminarBtn.setVisible(false);
        cancelarBtn.setVisible(false);
    }
}
