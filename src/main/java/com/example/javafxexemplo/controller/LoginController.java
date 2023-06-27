package com.example.javafxexemplo.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.example.javafxexemplo.HelloApplication;
import com.example.javafxexemplo.model.Usuario;
import com.example.javafxexemplo.model.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    TextField usuarioField;

    @FXML
    PasswordField senhaField;

    @FXML
    Label labelEntrar;

    @FXML
    public void entrar() throws IOException, SQLException {
        Usuario usuarioLogin = new Usuario(usuarioField.getText(), senhaField.getText());
//        usuarioLogin.usuario = usuarioField.getText();
//        usuarioLogin.senha = senhaField.getText();
        boolean usuarioExiste = new UsuarioDAO().existe(usuarioLogin);

        if (usuarioExiste) {
            // Usuário existe
            System.out.println("Entrando...");
            labelEntrar.setText("Entrando...");
            HelloApplication.setRoot("main-view");
        } else {
            // Usuário não existe
            System.out.println("Usuário ou senha incorretos!");
            labelEntrar.setText("Usuário ou senha incorretos!");
        }
    }

}