package com.usta.controllers;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;

import com.usta.App;
import com.usta.models.usuarios.Usuario;
import com.usta.models.usuarios.UsuarioImplDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class UsuarioController {

    UsuarioImplDAO usuarioDao = new UsuarioImplDAO();
    // ########################## para botones ##############
    @FXML
    private Button agregarBtn;
    @FXML
    private Button editarBtn;
    @FXML
    private Button eliminarBtn;
    @FXML
    private Button cancelarBtn;

    // ########################## fin botones ##############

    // ########################## para entrada de datos ##############
    @FXML
    private TextField documentoField;
    @FXML
    private TextField nombresField;
    @FXML
    private TextField apellidosField;
    @FXML
    private TextField correoField;
    // ########################## fin entrada de datos ##############

    // ########################## para la tabla ##############
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

    // ########################## fintabla ##############
    // ########################## para la búsqueda ##############
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
    // ########################## fin para la búsqueda ##############

    @FXML
    private ImageView fotoImg;
    File archivoFoto;
    FileOutputStream salida;
    @FXML
    private Label urlLbl;


    @FXML
    public void initialize() {

        documentoCol.setCellValueFactory(new PropertyValueFactory<>("documento"));
        nombresCol.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        apellidosCol.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        correoCol.setCellValueFactory(new PropertyValueFactory<>("correo"));
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
            boolean documentoMatch = documentoChk.isSelected() &&
                    usuario.getDocumento().toLowerCase().contains(filtroTxt);
            boolean nombresMatch = nombresChk.isSelected() &&
                    usuario.getNombres().toLowerCase().contains(filtroTxt);
            boolean apellidosMatch = apellidosChk.isSelected() &&
                    usuario.getApellidos().toLowerCase().contains(filtroTxt);
            boolean correoMatch = correoChk.isSelected() &&
                    usuario.getCorreo().toLowerCase().contains(filtroTxt);
            return documentoMatch || nombresMatch || apellidosMatch || correoMatch;
        });
        usuariosTable.setItems(usuariosFiltrados);
    }

    public byte[] convertirImagenABytes() {

        // Inicializar el arreglo de bytes
        byte[] bytesImg = new byte[(int) archivoFoto.length()];

        try {
            // Crear un flujo de entrada para leer el archivo
            FileInputStream fis = new FileInputStream(archivoFoto);

            // Leer el contenido del archivo en el arreglo de bytes
            fis.read(bytesImg);

            // Cerrar el flujo de entrada
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Devolver el arreglo de bytes
        return bytesImg;
    }

    public String guardarImagen(byte[] bytesImg) {

        String respuesta = null;
        try {
            salida = new FileOutputStream(
                    new File("src\\main\\java\\com\\usta\\view\\img\\usuarios\\" + documentoField.getText() + ".jpg"));
            salida.write(bytesImg);
            respuesta = "La imagen se guardo con exito.";
        } catch (Exception e) {
            System.out.println(e);
        }
        return respuesta;
    }
    @FXML
    public void agregarUsuario() {
        String documento = documentoField.getText();
        String nombres = nombresField.getText();
        String apellidos = apellidosField.getText();
        String correo = correoField.getText();
        String foto= fotoImg.toString();
        Usuario nuevoUsuario;
        if (fotoImg.getImage() != null) {
            nuevoUsuario = new Usuario(documento, nombres, apellidos, correo, foto);
            guardarImagen(convertirImagenABytes());
        } else {

            nuevoUsuario = new Usuario(documento, nombres, apellidos, correo);
        }
         new Usuario(documento, nombres, apellidos, correo);
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
        String foto = "";
        Usuario usuarioEditado = new Usuario(usuario.getId(), documento, nombres, apellidos, correo, clave, foto);
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
    public void cargarFoto() {
       FileChooser fc = new FileChooser();
        fc.setTitle("Subir Imagen");
        fc.getExtensionFilters().add(new ExtensionFilter("Imágenes", "*.jpg"));
        archivoFoto = fc.showOpenDialog(null);

        if (archivoFoto != null) {
            urlLbl.setText("" + archivoFoto.getAbsolutePath());
            Image image = new Image(archivoFoto.toURI().toString());
            fotoImg.setImage(image);
        }

    }

    @FXML
    public void seleccionarUsuario() {
        Usuario usuario = usuariosTable.getSelectionModel().getSelectedItem();
        documentoField.setText(usuario.getDocumento());
        documentoField.setEditable(false);
        nombresField.setText(usuario.getNombres());
        apellidosField.setText(usuario.getApellidos());
        correoField.setText(usuario.getCorreo());

        Image image = new Image(usuario.getFoto());

        fotoImg.setImage(image);
        agregarBtn.setVisible(false);
        editarBtn.setVisible(true);
        eliminarBtn.setVisible(true);
        cancelarBtn.setVisible(true);

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

    @FXML
    public void limpiarCampos() {
        documentoField.setText("");
        documentoField.setEditable(true);
        nombresField.setText("");
        apellidosField.setText("");
        correoField.setText("");
        agregarBtn.setVisible(true);
        editarBtn.setVisible(false);
        eliminarBtn.setVisible(false);
        cancelarBtn.setVisible(false);
    }
}
