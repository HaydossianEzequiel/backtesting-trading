package api.model.strategies;

import api.model.StockContext;

import java.math.RoundingMode;

public class TrailingStopSellStrategy implements SellStrategy {
    //evaluar que para X ganancia actual, el target o el stop cambien en distinta proporcion
    //evaluar que cambien segun el regimen de volatilidad
    public Double stopPrice;
    public Double profitTargetPrice;

    public Integer initialStopPercentage;
    public Integer profitTargetPercentage;
    public Integer upperCachePercentage;


    public TrailingStopSellStrategy(Integer initialStop, Integer profitTargetPercentage, Integer upperCachePercentage) {
        if (profitTargetPercentage < upperCachePercentage) {
            throw new RuntimeException("profitTargetPercentage can not be less than upperCachePercentage");

        }
        if (initialStop < 1 && initialStop > 70) {
            throw new RuntimeException("Fixed Stop Loss invalid percentage");
        }

        if (profitTargetPercentage < 1 && profitTargetPercentage > 70) {
            throw new RuntimeException("Fixed Profit target invalid percentage");
        }

        this.initialStopPercentage = initialStop;
        this.profitTargetPercentage = profitTargetPercentage;
        this.upperCachePercentage = upperCachePercentage;
        this.stopPrice = 0d;
        this.profitTargetPrice = 0d;
    }

    public boolean shouldSell(StockContext stockContext) {
        if (stopPrice.intValue() == 0) {
            stopPrice = stockContext.positionPrice * ((double) (100 - initialStopPercentage) / 100);
            profitTargetPrice = stockContext.positionPrice * ((double) (100 + profitTargetPercentage) / 100);
        }
        //Esto no deberia ser parte del shouldSell. Seria un updateData que se ejecute genericamente en el abstract
        if (breakProfitTarget(stockContext)) {
            updateStopPrice();
            updateProfitTargetPrice();
            if (stopPrice > profitTargetPrice) {
                throw new RuntimeException("stop price can not be grater than target price");
            }
        }
        return breakStopLoss(stockContext);
    }

    private void updateProfitTargetPrice() {
        profitTargetPrice = profitTargetPrice * (1 + (double) (profitTargetPercentage) / 100);
    }

    private void updateStopPrice() {
        stopPrice = stopPrice * (1 + (double) (upperCachePercentage) / 100);
    }

    private boolean breakProfitTarget(StockContext stockContext) {
        return stockContext.actualPrice > profitTargetPrice;
    }

    private boolean breakStopLoss(StockContext stockContext) {
        return stockContext.actualPrice < stopPrice;
    }

    @Override
    public boolean hasData(StockContext stockContext) {
        return stockContext.positionPrice != null && stockContext.actualPrice != null;
    }


    @Override
    public String toString() {
        return getClass().getName() + initialStopPercentage.toString() + profitTargetPercentage.toString();
    }

}
