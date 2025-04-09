package school.sptech.etl.load;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.SQLException;

public class DatabaseLoader {

    private static final String URL = "jdbc:mysql://localhost:3306/flyon";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Suave2004@";

    public static void loadData(List<List<String>> batchCleanedDateTime,
                                List<List<String>> batchRawData,
                                List<Integer> batchAssentos) {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Conexão e desabilita autocommit
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);

            String query = "INSERT INTO historico_passagens(data_hora_partida_prevista, data_hora_partida_real, " +
                    "data_hora_chegada_prevista, data_hora_chegada_real, sigla_empresa_aerea, " +
                    "empresa_aerea, origem, destino, situacao_voo, situacao_partida, " +
                    "situacao_chegada, assentos_comercializados) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);

            for (int i = 0; i < batchCleanedDateTime.size(); i++) {
                List<String> cleanedDateTime = batchCleanedDateTime.get(i);
                List<String> rawData = batchRawData.get(i);
                Integer assentos = batchAssentos.get(i);

                stmt.setString(1, cleanedDateTime.get(0));
                stmt.setString(2, cleanedDateTime.get(1));
                stmt.setString(3, cleanedDateTime.get(2));
                stmt.setString(4, cleanedDateTime.get(3));
                stmt.setString(5, rawData.get(0));
                stmt.setString(6, rawData.get(1));
                stmt.setString(7, rawData.get(2));
                stmt.setString(8, rawData.get(3));
                stmt.setString(9, rawData.get(4));
                stmt.setString(10, rawData.get(5));
                stmt.setString(11, rawData.get(6));
                stmt.setInt(12, assentos);

                stmt.addBatch(); // Adiciona tudo de uma vez
            }

            stmt.executeBatch(); // Executa todo o lote
            conn.commit();       // Commit único
            System.out.println("Commit realizado - " + batchCleanedDateTime.size() + " registros inseridos");

        } catch (SQLException e) {
            // Rollback em caso de erro
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transação revertida devido a erro");
                } catch (SQLException ex) {
                    System.err.println("Erro ao reverter transação: " + ex.getMessage());
                }
            }
            e.printStackTrace();
        } finally {
            // Fecha tudo
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
