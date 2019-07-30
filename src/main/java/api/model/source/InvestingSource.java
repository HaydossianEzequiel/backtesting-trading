package api.model.source;

import api.model.DataStock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvestingSource {

    public List<DataStock> getHistoricalData(String stockName) throws IOException {
        String fullName = new File("").getAbsolutePath() + "/src/main/java/database/" + stockName + ".csv";
        BufferedReader br = new BufferedReader(new FileReader(fullName));
        String line = null;
        List<DataStock> dataStocks = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String str[] = line.split("@");
            dataStocks.add(new DataStock(str[0], str[1]));
        }
        return dataStocks;
    }


}
