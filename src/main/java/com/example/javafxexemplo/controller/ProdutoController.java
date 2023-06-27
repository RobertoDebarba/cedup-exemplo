package com.example.javafxexemplo.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.example.javafxexemplo.HelloApplication;
import com.example.javafxexemplo.model.ProdutoDAO;
import com.example.javafxexemplo.model.Produto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProdutoController implements Initializable {

    @FXML
    TableView<Produto> tabelaProdutos;

    @FXML
    TableColumn<Produto, Integer> colunaCodigo;

    @FXML
    TableColumn<Produto, String> colunaNome;

    @FXML
    TableColumn<Produto, Double> colunaPreco;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        Configura as colunas da tabela da interface gráfica
         */
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        ProdutoDAO produtoDAO = new ProdutoDAO();
        try {
            List<Produto> produtos = produtoDAO.getAll();
            tabelaProdutos.getItems().addAll(produtos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void novo() throws IOException, SQLException {
        ProdutoModalController.produto = null;

        HelloApplication.showModal("produto-modal-view");

        // O modal foi fechado
        Produto novoProduto = ProdutoModalController.produto;

        if (novoProduto != null) {
            new ProdutoDAO().insert(novoProduto);
            tabelaProdutos.getItems().add(novoProduto);
        }
    }

    @FXML
    public void editar() throws IOException, SQLException {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        ProdutoModalController.produto = produtoSelecionado;

        HelloApplication.showModal("produto-modal-view");

        // O modal de edição foi salvo
        Produto produtoEditado = ProdutoModalController.produto;

        produtoSelecionado.codigo = produtoEditado.codigo;
        produtoSelecionado.nome = produtoEditado.nome;
        produtoSelecionado.preco = produtoEditado.preco;

        new ProdutoDAO().update(produtoEditado);
        tabelaProdutos.refresh();
    }

    @FXML
    public void excluir() throws SQLException {
        // Obter o produto selecionado
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        // Confirmação de exclusão
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Excluir produto");
        alert.setHeaderText(produtoSelecionado.codigo + " " + produtoSelecionado.nome + " R$" + produtoSelecionado.preco);
        alert.setContentText("Deseja excluir o produto?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // Excluir o produto
            tabelaProdutos.getItems().remove(produtoSelecionado);
            new ProdutoDAO().delete(produtoSelecionado);
        }
    }
}
