package api.model.strategies;

import api.model.StockContext;

public class TrailingStopSellStrategy implements SellStrategy {
    //evaluar que para X ganancia actual, el target o el stop cambien en distinta proporcion
    //evaluar que cambien segun el regimen de volatilidad

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

    }

    public boolean shouldSell(StockContext stockContext) {
        if (stockContext.stopPrice == 0) {
            stockContext.stopPrice = stockContext.positionPrice * ((double) (100 - initialStopPercentage) / 100);
            stockContext.profitTargetPrice = stockContext.positionPrice * ((double) (100 + profitTargetPercentage) / 100);
        }
        //Esto no deberia ser parte del shouldSell. Seria un updateData que se ejecute genericamente en el abstract
        if (breakProfitTarget(stockContext)) {
            updateStopPrice(stockContext);
            updateProfitTargetPrice(stockContext);
            if (stockContext.stopPrice > stockContext.profitTargetPrice) {
                throw new RuntimeException("stop price can not be grater than target price");
            }
        }
        return breakStopLoss(stockContext);
    }

    public void updateProfitTargetPrice(StockContext stockContext) {
        stockContext.profitTargetPrice = stockContext.profitTargetPrice * (1 + (double) (profitTargetPercentage) / 100);
    }

    public void updateStopPrice(StockContext stockContext) {
        stockContext.stopPrice = stockContext.stopPrice * (1 + (double) (upperCachePercentage) / 100);
    }

    private boolean breakProfitTarget(StockContext stockContext) {
        return stockContext.actualPrice > stockContext.profitTargetPrice;
    }

    private boolean breakStopLoss(StockContext stockContext) {
        return stockContext.actualPrice < stockContext.stopPrice;
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
