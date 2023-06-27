package com.example.javafxexemplo.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.javafxexemplo.ConnectionSingleton;
import com.example.javafxexemplo.model.Produto;

public class ProdutoDAO {
    /*
    TODO
    //prepareStatement ? login
    //close statement
    //ConnectionSingleton
    //Desabilitar campo código com auto increment
    //Todos os botões funcionais
    //Validar formato do número
    TextField numérico
    MVC
    Git

    Prova:
    Não fazer nada quando não houver produto selecionado
    Validar obrigatoriedade dos campos
     */

    public List<Produto> getAll() throws SQLException {
        try (Statement statement = ConnectionSingleton.getConnection().createStatement(); ResultSet rs = statement.executeQuery("select * from produto")) {

            List<Produto> produtos = new ArrayList<>();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.codigo = rs.getInt(1);
                produto.nome = rs.getString(2);
                produto.preco = rs.getDouble(3);
                produtos.add(produto);
            }

            return produtos;
        }
    }

    public void insert(Produto novoProduto) throws SQLException {
        String sql = "insert into produto (nome, preco) values (?, ?)";
        try (PreparedStatement preparedStatement =
                ConnectionSingleton.getConnection()
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, novoProduto.nome);
            preparedStatement.setDouble(2, novoProduto.preco);

            preparedStatement.execute();

            // Obtém o ID do registro inserido
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                novoProduto.codigo = rs.getInt(1);
            }
        }
    }

    public void update(Produto produtoEditado) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement("update produto set nome = ?, preco = ? where codigo = ?")) {
            preparedStatement.setString(1, produtoEditado.nome);
            preparedStatement.setDouble(2, produtoEditado.preco);
            preparedStatement.setInt(3, produtoEditado.codigo);
            preparedStatement.execute();
        }
    }

    public void delete(Produto produtoRemovido) throws SQLException {
        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement("delete from produto where codigo = ?")) {
            preparedStatement.setInt(1, produtoRemovido.codigo);
            preparedStatement.execute();
        }
    }

}
