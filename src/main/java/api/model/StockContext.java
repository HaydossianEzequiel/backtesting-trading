package api.model;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockContext {

    public String stockName;
    public Double fiveMovingAverage;
    public Double twentyMovingAverage;
    public Map<Integer, Double> movingAverage = new HashMap<>();

    public String actualDate;
    public List<Double> lastPricesFiveMovingAverage = new ArrayList<>();
    public List<Double> lastPricesTwentyMovingAverage = new ArrayList<>();

    public Map<Integer, List<Double>> lastPricesMovingAverage = new HashMap<>();

    public List<OperationResult> operationResults = new ArrayList<>();
    public List<DataStock> dataStocks;

    public Double positionPrice; // para identificar si actualmente estoy comprado
    public String positionDay; // para identificar si actualmente estoy comprado


    public StockContext(String stockName, List<DataStock> historicalData) {
        this.stockName = stockName;
        dataStocks = Lists.reverse(historicalData);
    }


    public void update(Operation operation, Double actualPrice) {
        if (operation.equals(Operation.BUY)) {
            updateBuy(actualPrice);
        } else if (operation.equals(Operation.SELL)) {
            updateSell(actualPrice);
        }

    }

    private void updateBuy(Double actualPrice) {
        positionPrice = actualPrice;
        positionDay = actualDate;

    }

    private void updateSell(Double actualPrice) {
        OperationResult operationResult = new OperationResult();
        if (positionPrice > actualPrice) {
            operationResult.result = "win";
        } else {
            operationResult.result = "loss";
        }
        positionPrice = null;


        operationResult.buyDate = positionDay;
        positionDay = null;

        operationResult.sellDate = actualDate;
        operationResults.add(operationResult);
    }
}
