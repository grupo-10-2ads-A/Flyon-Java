package school.sptech.etl.extract;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;

public class XlsxExtractor {

    // Cache para otimização
    private static Workbook cachedWorkbook = null;
    private static String cachedFilePath = null;

    public static Integer returnTotalRows(String filePath) throws IOException {
        initWorkbookCache(filePath);
        Sheet sheet = cachedWorkbook.getSheetAt(0);
        return sheet.getLastRowNum();
    }

    public static List<String> extractData(String filePath, int currentRow) throws IOException {
        initWorkbookCache(filePath);
        Sheet sheet = cachedWorkbook.getSheetAt(0);
        Row row = sheet.getRow(currentRow);


        List<String> res = new ArrayList();

        if (row != null) {
            row.getCell(6).setCellType(CellType.STRING);

            String data_hora_partida_prevista = formatarDataHoraCelula(row.getCell(9));
            String data_hora_partida_real = formatarDataHoraCelula(row.getCell(10));
            String data_hora_chegada_prevista = formatarDataHoraCelula(row.getCell(13));
            String data_hora_chegada_real = formatarDataHoraCelula(row.getCell(14));
            String sigla_empresa_aerea = formatarSeNulo(row.getCell(0));
            String empresa_aerea = formatarSeNulo(row.getCell(1));
            String origem = formatarSeNulo(row.getCell(8));
            String destino = formatarSeNulo(row.getCell(12));
            String situacao_voo = formatarSeNulo(row.getCell(15));
            String situacao_partida = formatarSeNulo(row.getCell(18));
            String situacao_chegada = formatarSeNulo(row.getCell(19));
            String assentos_comercializados = formatarSeNulo(row.getCell(6));

            Collections.addAll(res,
                    data_hora_partida_prevista,
                    data_hora_partida_real,
                    data_hora_chegada_prevista,
                    data_hora_chegada_real,
                    sigla_empresa_aerea,
                    empresa_aerea,
                    origem,
                    destino,
                    situacao_voo,
                    situacao_partida,
                    situacao_chegada,
                    assentos_comercializados
            );
        }

        return res;
    }

    // Novo método otimizado para leitura em lote
    public static List<List<String>> extractBatchData(String filePath, int startRow, int batchSize) throws IOException {
        initWorkbookCache(filePath);
        Sheet sheet = cachedWorkbook.getSheetAt(0);
        List<List<String>> batchData = new ArrayList<>(batchSize);

        int endRow = Math.min(startRow + batchSize - 1, sheet.getLastRowNum());

        for (int rowNum = startRow; rowNum <= endRow; rowNum++) {
            batchData.add(extractData(filePath, rowNum));
        }

        return batchData;
    }

    // Método para fechar o workbook quando terminar
    public static void closeWorkbook() throws IOException {
        if (cachedWorkbook != null) {
            cachedWorkbook.close();
            cachedWorkbook = null;
            cachedFilePath = null;
        }
    }

    private static String formatarSeNulo(Cell cell) {
        if (cell == null) {
            return null;
        }
        return cell.getStringCellValue();
    }

    public static String formatarDataHoraCelula(Cell cell) {
        if (cell == null) {
            return null;
        }

        // Verifica se a célula contém uma data
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            // Cria um formatador de data
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            // Obtém o valor da data e formata
            return dateFormat.format(cell.getDateCellValue());
        }
        // Se não for uma data, retorna o valor como string
        return cell.toString();
    }

    // Método auxiliar para gerenciar o cache
    private static void initWorkbookCache(String filePath) throws IOException {
        if (!filePath.equals(cachedFilePath)) {
            if (cachedWorkbook != null) {
                cachedWorkbook.close();
            }
            cachedWorkbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            cachedFilePath = filePath;
        }
    }
}