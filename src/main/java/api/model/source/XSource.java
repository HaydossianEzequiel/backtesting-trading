package api.model.source;

import api.model.DataStock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XSource {

    public List<DataStock> getHistoricalData(String stockName) throws IOException {
        String fullName = new File("").getAbsolutePath() + "/src/main/java/database/" + stockName + ".csv";
        BufferedReader br = new BufferedReader(new FileReader(fullName));
        String line = null;
        List<DataStock> dataStocks = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String str[] = line.split(", ");
            if (!str[0].equals("Date")) {
                String date = str[0].replace(".", ",");
                String close = str[1].replace(".", ",");
                dataStocks.add(new DataStock(date, close));
            }

        }
        return dataStocks;
    }


}
