package api.model;

import api.model.source.XSource;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    public Portfolio(List<String> stocks) {
        stocks.forEach(stock -> {
            stockContexts.add(new StockContext(stock, new XSource().getHistoricalData(stock)));
        });
        int initSize = stockContexts.get(0).dataStocks.size();
        stockContexts.forEach(it -> {
            if(it.dataStocks.size() != initSize){
                throw new RuntimeException("Incompatible data size");
            }
        });
    }


    public List<StockContext> stockContexts = new ArrayList<>();

    public void closeAllPositions(int finalDay) {
        stockContexts.forEach( it -> {
            try {
                if(it.positionPrice != null){ //validar
                    it.update(Operation.SELL, DecimalFormat.getNumberInstance().parse(it.dataStocks.get(finalDay).close).doubleValue());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }
}
