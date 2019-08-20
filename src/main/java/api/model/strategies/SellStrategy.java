package api.model.strategies;

import api.model.StockContext;

public interface SellStrategy {

    boolean shouldSell(StockContext stockContext);

    boolean hasData(StockContext stockContext);

}
