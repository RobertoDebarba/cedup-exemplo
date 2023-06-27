package com.example.javafxexemplo.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.javafxexemplo.ConnectionSingleton;
import com.example.javafxexemplo.model.Usuario;

public class UsuarioDAO {

    public boolean existe(Usuario usuario) throws SQLException {
        String sql = "select count(*) from usuario where usuario = ? AND senha = ?";

        try (PreparedStatement preparedStatement = ConnectionSingleton.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, usuario.usuario);
            preparedStatement.setString(2, usuario.senha);

            try (ResultSet resultado = preparedStatement.executeQuery()) {
                resultado.next();
                int quantidadeUsuarios = resultado.getInt(1);

                if (quantidadeUsuarios > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

}
