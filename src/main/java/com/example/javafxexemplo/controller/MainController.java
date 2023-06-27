package com.example.javafxexemplo.controller;

import java.io.IOException;

import com.example.javafxexemplo.HelloApplication;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    public void abrirTelaProduto() throws IOException {
        HelloApplication.setRoot("produto-view");
    }

    @FXML
    public void sair() throws IOException {
        HelloApplication.setRoot("login-view");
    }

}
