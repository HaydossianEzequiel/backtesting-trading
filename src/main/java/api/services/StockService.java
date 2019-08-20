package api.services;

import api.model.*;
import api.model.strategies.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class StockService {

    public List<OperationResult> getOperationResults(Strategy strategy) throws ParseException {
        Portfolio portfolio = run(strategy);
        List<OperationResult> operationResults = new ArrayList<>();
        portfolio.stockContexts.forEach(it -> operationResults.addAll(it.operationResults));
        return operationResults;
    }

    public List<Metric> getBestMovingAverageMetrics() throws IOException, ParseException {
        List<Metric> metrics = getMovingAverageMetrics();
        List<Metric> bestMetrics = getBestMetrics(metrics);

        return bestMetrics;
    }

    private List<Metric> getBestMetrics(List<Metric> metrics) {
        List<Metric> bestMetrics = metrics.stream().filter(it ->
                ((double) it.wins / it.totalOperations) * 100 > 70)
                .collect(Collectors.toList());

        return bestMetrics;
    }


    public List<Metric> getMovingAverageMetrics() throws IOException, ParseException {
        List<Metric> metrics = new ArrayList<>();
        for (int slow = 7; slow < 150; slow++) {
            for (int fast = 2; fast < 100; fast++) {
                if (slow > fast) { //quien dice que esto tiene que ser asi?...
                    Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(slow, fast), new CrossMovingAverageSellStrategy(slow, fast));
                    metrics.add(getMetrics(strategy));
                }

            }

        }
        return metrics;
    }

    public Metric getMetrics(Strategy strategy) throws ParseException {
        List<OperationResult> operationResults = getOperationResults(strategy);

        Metric metric = new Metric();
        metric.strategyName = strategy.toString();
        metric.wins = operationResults.stream().filter(it -> it.result.equals("win")).collect(Collectors.toList()).size();
        metric.loss = operationResults.stream().filter(it -> it.result.equals("loss")).collect(Collectors.toList()).size();
        metric.totalOperations = operationResults.size();
        List<OperationResult> sortedOperations = operationResults.stream().sorted(Comparator.comparingDouble(OperationResult::getMovement)).collect(Collectors.toList());
        metric.worstMovement = sortedOperations.get(0);
        metric.bestMovement = sortedOperations.get(operationResults.size() - 1);
        metric.globalResult = getGlobalResult(operationResults);


        return metric;

    }

    private Double getGlobalResult(List<OperationResult> operationResults) {
        Double cash = 1000d;
        for (int i = 0; i < operationResults.size(); i++) {
            cash = cash * (1 + ((operationResults.get(i).movement / 100)));
        }
        return cash;

    }

    public List<DataStock> getStocks() throws ParseException {
        Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(20, 5), new CrossMovingAverageSellStrategy(20, 5));
        Portfolio portfolio = run(strategy);
        return portfolio.stockContexts.get(0).dataStocks;

    }

    private Portfolio run(Strategy strategy) throws ParseException {
        String macy = "M";
        String generalElectric = "GE";
        String MMM = "MMM";
        List<String> stocks = new ArrayList<>();
        //stocks.add(macy);
        //stocks.add(generalElectric);
        stocks.add(generalElectric);


        Portfolio portfolio = new Portfolio(stocks);
        int finalDay = portfolio.stockContexts.get(0).dataStocks.size() - 1;
        for (int day = 0; day <= finalDay; day++) {
            for (int i = 0; i < stocks.size(); ++i) {
                runDay(portfolio.stockContexts.get(i), day, strategy);
            }
        }

        portfolio.closeAllPositions(finalDay);

        return portfolio;
    }


    private void runDay(StockContext stockContext, int day, Strategy strategy) throws ParseException {

        updateDataContext(stockContext.dataStocks, stockContext, day, strategy);

        Operation operation = strategy.getOperation(stockContext);
        if (operation != null) {
            stockContext.update(operation, DecimalFormat.getNumberInstance().parse(stockContext.dataStocks.get(day).close).doubleValue());
        }
    }


    private void updateDataContext(List<DataStock> dataStocks, StockContext stockContext, int i, Strategy strategy) throws ParseException {
        DataStock actualDataStock = dataStocks.get(i);
        stockContext.actualDate = actualDataStock.date;
        stockContext.actualPrice = DecimalFormat.getNumberInstance().parse(actualDataStock.close).doubleValue();

        strategy.buyStrategy.updateData(this, stockContext, actualDataStock);


    }


    public void updateMovingAverage(StockContext stockContext, Integer average, DataStock actualDataStock) {
        try{
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
        } catch(ParseException e){
            throw new RuntimeException(e);
        }

    }


}
