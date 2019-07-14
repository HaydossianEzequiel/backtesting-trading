package api.model;

import java.util.HashMap;
import java.util.Map;

public class DataStock {
    public String date;
    public String lastPrice;
    public String open;
    public String low;
    public String close;
    public Map<Integer, Double> movingAverage = new HashMap();
    public Double fiveMovingAverage; // deberia salir del context
    public Double twentyMovingAverage;//deberia salir del context

    public DataStock(String date, String lastPrice, String open, String low, String close) {
        this.date = date;
        this.lastPrice = lastPrice;
        this.open = open;
        this.low = low;
        this.close = close;
    }

}
