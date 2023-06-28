package com.ciandt.nextgen.bootcamp;

import java.sql.*;

public class TesteConexao {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            // Carrega a classe do driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conecta ao banco de dados
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccine-la", "root", "");

            // Executa uma consulta SQL
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM campaigns");

            // Processa o resultado da consulta
            while (rs.next()) {
                // faz algo aqui
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}







