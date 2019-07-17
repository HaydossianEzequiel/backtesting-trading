package api.model;

import java.util.HashMap;
import java.util.Map;

public class DataStock {
    public String date;
    public String close;
    public Map<Integer, Double> movingAverage = new HashMap();

    public DataStock(String date, String close) {
        this.date = date;
        this.close = close;

    }

}
