package api.model.strategies;

import api.model.StockContext;

interface SellStrategy {

    boolean shouldSell(StockContext stockContext);

    boolean hasData(StockContext stockContext);

}
