package api.services;

import api.model.DataStock;
import api.model.Operation;
import api.model.OperationResult;
import api.model.StockContext;
import api.model.strategies.CrossMovingAverageStrategy;
import api.model.strategies.Strategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class StockService {

    public List<OperationResult> getOperationResults() throws IOException, ParseException {
        StockContext stockContext = run();
        return stockContext.operationResults;
    }

    public List<DataStock> getStocks() throws IOException, ParseException {
        StockContext stockContext = run();
        return stockContext.dataStocks;
    }

    private StockContext run() throws IOException, ParseException {
        String stockName = "ggal";

        StockContext stockContext = new StockContext(stockName, getHistoricalData(stockName));

        for (int day = 0; day < stockContext.dataStocks.size(); day++) {
            runDay(stockContext, day);
        }
        return stockContext;
    }

    private void runDay(StockContext stockContext, int day) throws ParseException {
        updateDataContext(stockContext.dataStocks, stockContext, day);
        Strategy strategy = new CrossMovingAverageStrategy();
        Operation operation = strategy.getOperation(stockContext);
        if (operation != null) {
            stockContext.update(operation, DecimalFormat.getNumberInstance().parse(stockContext.dataStocks.get(day).close).doubleValue());
        }
    }


    private void updateDataContext(List<DataStock> dataStocks, StockContext stockContext, int i) throws ParseException {
        DataStock actualDataStock = dataStocks.get(i);
        stockContext.actualDate = actualDataStock.date;
        updateFiveMovingAverage(stockContext, actualDataStock);
        updateTwentyMovingAverage(stockContext, actualDataStock);

    }

    private void updateTwentyMovingAverage(StockContext stockContext, DataStock actualDataStock) throws ParseException {

        if (stockContext.lastPricesTwentyMovingAverage.size() >= 20) {
            stockContext.lastPricesTwentyMovingAverage.remove(0);
            stockContext.lastPricesTwentyMovingAverage.add(DecimalFormat.getNumberInstance().parse(actualDataStock.close).doubleValue());
            stockContext.twentyMovingAverage = stockContext.lastPricesTwentyMovingAverage.stream().mapToDouble(a -> a).average().getAsDouble();
            actualDataStock.twentyMovingAverage = stockContext.twentyMovingAverage;

        } else {
            stockContext.lastPricesTwentyMovingAverage.add(DecimalFormat.getNumberInstance().parse(actualDataStock.close).doubleValue());
        }
    }

    private void updateFiveMovingAverage(StockContext stockContext, DataStock actualDataStock) throws ParseException {
        if (stockContext.lastPricesFiveMovingAverage.size() >= 5) {
            stockContext.lastPricesFiveMovingAverage.remove(0);
            stockContext.lastPricesFiveMovingAverage.add(DecimalFormat.getNumberInstance().parse(actualDataStock.close).doubleValue());
            stockContext.fiveMovingAverage = stockContext.lastPricesFiveMovingAverage.stream().mapToDouble(a -> a).average().getAsDouble();
            actualDataStock.fiveMovingAverage = stockContext.fiveMovingAverage;

        } else {
            stockContext.lastPricesFiveMovingAverage.add(DecimalFormat.getNumberInstance().parse(actualDataStock.close).doubleValue());
        }
    }

    private List<DataStock> getHistoricalData(String stockName) throws IOException {
        String fullName = new File("").getAbsolutePath() + "/src/main/java/database/" + stockName + ".csv";
        BufferedReader br = new BufferedReader(new FileReader(fullName));
        String line = null;
        List<DataStock> dataStocks = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String str[] = line.split("@");
            dataStocks.add(new DataStock(str[0], str[1], str[2], str[3], str[4]));
        }
        return dataStocks;
    }


}
