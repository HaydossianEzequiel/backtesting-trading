package api.model.strategies;

import api.model.Operation;
import api.model.StockContext;

public class Strategy {

    public final BuyStrategy buyStrategy;
    public final SellStrategy sellStrategy;

    public Strategy(BuyStrategy buyStrategy, SellStrategy sellStrategy) {
        this.buyStrategy = buyStrategy;
        this.sellStrategy = sellStrategy;
    }

    public Operation getOperation(StockContext stockContext) {

        Operation operation = Operation.SKIP;

        if (!buyStrategy.hasData(stockContext)) {
            return operation;
        }

        if (buyStrategy.shouldBuy(stockContext)) {
            operation = Operation.BUY;
        }

        if (!sellStrategy.hasData(stockContext)) {
            return operation;
        }

        if (sellStrategy.shouldSell(stockContext)) {
            if (operation.equals(Operation.BUY)) {
                throw new RuntimeException("can not should sell and buy");
            }
            operation = Operation.SELL;
        }

        return operation;

    }

    @Override
    public String toString() {
        return buyStrategy.toString() + "-" + sellStrategy.toString();
    }


}
