package api.model.strategies;

import api.model.DataStock;
import api.model.StockContext;
import api.services.StockService;

public class BasicMomentumBuyStrategy implements BuyStrategy {
    private Integer rangeDays;
    private Double toleranceToMin;
    private Double toleranceToMax;
    private Double minRangePercentage;
    //Solo puedo usarlo con los days de una media movil
    //Ojo al updateData

    public BasicMomentumBuyStrategy(Integer range, Double toleranceToMin, Double toleranceToMax, Double minRangePercentage) {
        this.rangeDays = range;
        this.toleranceToMin = toleranceToMin;
        this.toleranceToMax = toleranceToMax;
        this.minRangePercentage = minRangePercentage;
    }

    @Override
    public boolean shouldBuy(StockContext stockContext) {
        Double minPercentage = (stockContext.actualPrice / stockContext.minLastDays.get(rangeDays)) - 1;
        Double maxPercentage = (stockContext.actualPrice / stockContext.maxLastDays.get(rangeDays)) - 1;
        Double rangePercentage = stockContext.minLastDays.get(rangeDays) / stockContext.maxLastDays.get(rangeDays);
        //return minPercentage < toleranceToMin && maxPercentage > toleranceToMax && rangePercentage < minRangePercentage ;
        return maxPercentage > toleranceToMax; //&& rangePercentage < minRangePercentage ;


    }

    @Override
    public boolean hasData(StockContext stockContext) {
        return stockContext.minLastDays.containsKey(rangeDays) && stockContext.maxLastDays.containsKey(rangeDays);
    }

    @Override
    public void updateData(StockService stockService, StockContext stockContext, DataStock actualDataStock) {
        //Por ahora no actualizo nada porque uso el update de las medias moviles
    }
}
