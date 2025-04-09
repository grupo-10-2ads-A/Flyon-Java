package school.sptech;

import school.sptech.etl.extract.XlsxExtractor;
import school.sptech.etl.transform.DataTransformer;
import school.sptech.etl.load.DatabaseLoader;

import java.util.ArrayList;
import java.util.List;

public class EtlProcess {

    private static final int TEST_LIMIT = 500;       // Limite de 500 linhas
    private static final int EXTRACT_BATCH_SIZE = 50; // Lê 50 linhas do Excel por vez
    private static final int  DB_BATCH_SIZE = 10;      // Insere 10 linhas no banco por lote

    public static void main(String[] args) {

        try {
            String filePath = "C:\\Users\\vit_o\\FlyOn\\projeto-etl-xlsx\\src\\main\\resources\\VRA_2022_01.xlsx";
            int totalRows = XlsxExtractor.returnTotalRows(filePath);
            int rowsToProcess = Math.min(totalRows, TEST_LIMIT);

            System.out.println("Iniciando ETL para " + rowsToProcess + " linhas...");

            // Listas para acumular os lotes
            List<List<String>> batchCleanedDateTime = new ArrayList<>(DB_BATCH_SIZE);
            List<List<String>> batchCleanedData = new ArrayList<>(DB_BATCH_SIZE);
            List<Integer> batchAssentos = new ArrayList<>(DB_BATCH_SIZE);

            // Processa em blocos maiores de extração
            for (int startRow = 1; startRow <= rowsToProcess; startRow += EXTRACT_BATCH_SIZE) {
                int currentBatchSize = Math.min(EXTRACT_BATCH_SIZE, rowsToProcess - startRow + 1);

                // Extrai um bloco de linhas (otimizado)
                List<List<String>> batchRawData = XlsxExtractor.extractBatchData(filePath, startRow, currentBatchSize);

                // Transforma cada linha do bloco
                for (List<String> rawData : batchRawData) {
                    List<String> cleanedDateTime = DataTransformer.returnDateTimes(rawData);
                    int assentos_comercializados = DataTransformer.transfomDataInt(rawData.get(rawData.size() - 1));
                    List<String> cleanedData = DataTransformer.transformData(rawData);

                    // Adiciona aos lotes de banco de dados
                    batchCleanedDateTime.add(cleanedDateTime);
                    batchCleanedData.add(cleanedData);
                    batchAssentos.add(assentos_comercializados);

                    // Envia para o banco quando o lote estiver cheio
                    if (batchCleanedDateTime.size() >= DB_BATCH_SIZE) {
                        DatabaseLoader.loadData(batchCleanedDateTime, batchCleanedData, batchAssentos);

                        // Limpa os lotes
                        batchCleanedDateTime.clear();
                        batchCleanedData.clear();
                        batchAssentos.clear();
                    }
                }

                System.out.println("Processado: " + Math.min(startRow + currentBatchSize - 1, rowsToProcess) + " de " + rowsToProcess);
            }

            // Envia quaisquer registros restantes
            if (!batchCleanedDateTime.isEmpty()) {
                DatabaseLoader.loadData(batchCleanedDateTime, batchCleanedData, batchAssentos);
            }

            // Fecha o workbook
            XlsxExtractor.closeWorkbook();

            System.out.println("ETL concluído com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro no ETL:");
            e.printStackTrace();
        }
    }
}
