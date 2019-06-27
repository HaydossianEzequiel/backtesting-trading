package api.model;

import java.util.ArrayList;
import java.util.List;

public class StockContext {
    public Double fiveMovingAverage;
    public Double twentyMovingAverage;
    public List<Double> lastPricesFiveMovingAverage = new ArrayList<>();
    public List<Double> lastPricesTwentyMovingAverage = new ArrayList<>();

}
