package api.services;

import api.model.DataStock;
import api.model.StockContext;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;



public class StockService {

    public List<DataStock> getStocks() throws IOException, ParseException {

        String fullName = new File("").getAbsolutePath() + "/src/main/java/database/ggal.csv";

        BufferedReader br = new BufferedReader(new FileReader(fullName));
        String line = null;
        List<DataStock> dataStocks = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String str[] = line.split("@");
            dataStocks.add(new DataStock(str[0], str[1], str[2], str[3], str[4]));
        }

        List<DataStock> reversedDataStocks = Lists.reverse(dataStocks);
        StockContext stockContext = new StockContext();
        for (int i = 0; i < reversedDataStocks.size(); i++) {
            DataStock actualDataStock = reversedDataStocks.get(i);
            if (stockContext.lastPricesFiveMovingAverage.size() >= 5) {
                stockContext.lastPricesFiveMovingAverage.remove(0);
                stockContext.lastPricesFiveMovingAverage.add(DecimalFormat.getNumberInstance().parse(actualDataStock.close).doubleValue());
                stockContext.fiveMovingAverage = stockContext.lastPricesFiveMovingAverage.stream().mapToDouble(a -> a).average().getAsDouble();
                actualDataStock.fiveMovingAverage = stockContext.fiveMovingAverage;
            }else {
                stockContext.lastPricesFiveMovingAverage.add(DecimalFormat.getNumberInstance().parse(actualDataStock.close).doubleValue());
                stockContext.lastPricesTwentyMovingAverage.add(DecimalFormat.getNumberInstance().parse(actualDataStock.close).doubleValue());

            }
        }
        return reversedDataStocks;
    }


}
