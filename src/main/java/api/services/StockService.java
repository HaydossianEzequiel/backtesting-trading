package api.services;

import api.model.*;
import api.model.strategies.CrossMovingAverageStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class StockService {

    public List<OperationResult> getOperationResults(CrossMovingAverageStrategy strategy) throws IOException, ParseException {
        StockContext stockContext = run(strategy);
        return stockContext.operationResults;
    }

    public List<Metric> getBestMovingAverageMetrics() throws IOException, ParseException {
        List<Metric> metrics = getMovingAverageMetrics();
        List<Metric> bestMetrics = getBestMetrics(metrics);

        return bestMetrics;
    }

    private List<Metric> getBestMetrics(List<Metric> metrics) {
        List<Metric> bestMetrics = metrics.stream().filter(it ->
                ((double)it.wins/it.totalOperations)*100 > 70)
                .collect(Collectors.toList());

        return bestMetrics;
    }


    public List<Metric> getMovingAverageMetrics() throws IOException, ParseException {
        List<Metric> metrics = new ArrayList<>();
        for (int slow = 7; slow < 150; slow++) {
            for (int fast = 2; fast < 100; fast++) {
                if(slow>fast){ //quien dice que esto tiene que ser asi?...
                    CrossMovingAverageStrategy strategy = new CrossMovingAverageStrategy(slow, fast);
                    metrics.add(getMetrics(strategy));
                }

            }

        }
        return metrics;
    }

    public Metric getMetrics(CrossMovingAverageStrategy strategy) throws IOException, ParseException {
        List<OperationResult> operationResults = getOperationResults(strategy);
        Integer wins = operationResults.stream().filter(it -> it.result.equals("win")).collect(Collectors.toList()).size();
        Integer loss = operationResults.stream().filter(it -> it.result.equals("loss")).collect(Collectors.toList()).size();
        Integer totalOperations = operationResults.size();

        return new Metric("Cross" + strategy.fast.toString() + "-" + strategy.slow.toString(), wins, loss, totalOperations);

    }

    public List<DataStock> getStocks() throws IOException, ParseException {
        CrossMovingAverageStrategy strategy = new CrossMovingAverageStrategy(20, 5);
        StockContext stockContext = run(strategy);
        return stockContext.dataStocks;
    }

    private StockContext run(CrossMovingAverageStrategy strategy) throws IOException, ParseException {
        String stockName = "ggal";

        StockContext stockContext = new StockContext(stockName, getHistoricalData(stockName));

        for (int day = 0; day < stockContext.dataStocks.size(); day++) {
            runDay(stockContext, day, strategy);
        }
        return stockContext;
    }

    private void runDay(StockContext stockContext, int day, CrossMovingAverageStrategy strategy) throws ParseException {

        updateDataContext(stockContext.dataStocks, stockContext, day, strategy);

        Operation operation = strategy.getOperation(stockContext);
        if (operation != null) {
            stockContext.update(operation, DecimalFormat.getNumberInstance().parse(stockContext.dataStocks.get(day).close).doubleValue());
        }
    }


    private void updateDataContext(List<DataStock> dataStocks, StockContext stockContext, int i, CrossMovingAverageStrategy strategy) throws ParseException {
        DataStock actualDataStock = dataStocks.get(i);
        stockContext.actualDate = actualDataStock.date;
        updateMovingAverage(stockContext, strategy.fast, actualDataStock);
        updateMovingAverage(stockContext, strategy.slow, actualDataStock);

    }


    public void updateMovingAverage(StockContext stockContext, Integer average, DataStock actualDataStock) throws ParseException {
        if (!stockContext.lastPricesMovingAverage.containsKey(average)) {
            stockContext.lastPricesMovingAverage.put(average, new ArrayList<>());
        }

        if (stockContext.lastPricesMovingAverage.get(average).size() >= average) {
            stockContext.lastPricesMovingAverage.get(average).remove(0);
            stockContext.lastPricesMovingAverage.get(average).add(DecimalFormat.getNumberInstance().parse(actualDataStock.close).doubleValue());

            stockContext.movingAverage.put(average, stockContext.lastPricesMovingAverage.get(average).stream().mapToDouble(a -> a).average().getAsDouble());
            actualDataStock.movingAverage.put(average, stockContext.movingAverage.get(average));


        } else {
            stockContext.lastPricesMovingAverage.get(average).add(DecimalFormat.getNumberInstance().parse(actualDataStock.close).doubleValue());
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
