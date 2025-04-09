package school.sptech.etl.transform;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataTransformer {

    public static List<String> returnDateTimes(List<String> rawData) {
        List<String> dateTimes = new ArrayList<>();

        dateTimes.add(rawData.get(0));
        dateTimes.add(rawData.get(1));
        dateTimes.add(rawData.get(2));
        dateTimes.add(rawData.get(3));

        return transformDateTime(dateTimes);
    }

    public static List<String> transformDateTime(List<String> datasHoras) {
        if (datasHoras == null || datasHoras.size() != 4) {
            throw new IllegalArgumentException("Lista deve conter exatamente 4 elementos");
        }

        List<String> formatadas = new ArrayList<>();
        DateTimeFormatter entrada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter saida = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (String dh : datasHoras) {
            if (dh == null || dh.trim().isEmpty()) {
                formatadas.add(null);
                continue;
            }
            LocalDateTime dataHora = LocalDateTime.parse(dh, entrada);
            formatadas.add(dataHora.format(saida));
        }

        System.out.println("Sucesso - Transformação das datas e horas concluída");
        return formatadas;
    }

    public static List<String> transformData(List<String> rawData) {
        rawData.remove(3);
        rawData.remove(2);
        rawData.remove(1);
        rawData.remove(0);

        return rawData;
    }

    public static Integer transfomDataInt(String assentos_comercializados) {
        System.out.println("Success - Transformação do número de assentos bem sucedida");
        return Integer.parseInt(assentos_comercializados);
    }
}