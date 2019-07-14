package api.model.strategies;

import api.model.Operation;
import api.model.StockContext;

public class CrossMovingAverageStrategy implements Strategy {

    public final Integer slow;
    public final Integer fast;

    public CrossMovingAverageStrategy(Integer slow, Integer fast){
        this.slow = slow;
        this.fast = fast;
    }

    @Override
    public Operation getOperation(StockContext stockContext) {

        Operation operation = Operation.SKIP;

        if (!hasData(stockContext)) {
            return operation;
        }


        if (shouldBuy(stockContext)) {
            operation = Operation.BUY;
        }

        if (shouldSell(stockContext)) {
            if (operation.equals(Operation.BUY)) {
                throw new RuntimeException("can not should sell and buy");
            }
            operation = Operation.SELL;
        }

        return operation;

    }

    private boolean shouldSell(StockContext stockContext) {
        if (stockContext.positionPrice == null) {
            return false;
        }
        return stockContext.movingAverage.get(fast) < stockContext.movingAverage.get(slow);
    }

    private boolean shouldBuy(StockContext stockContext) {
        if (stockContext.positionPrice != null) {
            return false;
        }
        return stockContext.movingAverage.get(fast) > stockContext.movingAverage.get(slow);
    }


    private boolean hasData(StockContext stockContext) {
        return stockContext.movingAverage.containsKey(fast)&& stockContext.movingAverage.containsKey(slow);
    }
}
