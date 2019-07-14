package api.model.strategies;

import api.model.Operation;
import api.model.StockContext;

public interface Strategy {

    Operation getOperation(StockContext stockContext);

}
