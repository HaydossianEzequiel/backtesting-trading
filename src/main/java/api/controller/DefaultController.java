package api.controller;

import api.model.DataStock;
import api.model.Metric;
import api.model.OperationResult;
import api.model.strategies.CrossMovingAverageStrategy;
import api.services.StockService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class DefaultController {

    private static Gson gson = new Gson();
    private static StockService stockService = new StockService();

    public static Object getData(Request request, Response response) throws IOException, ParseException {

        List<DataStock> stocks = stockService.getStocks();
        return gson.toJson(stocks);
    }

    public static Object getOperationResults(Request request, Response response) throws IOException, ParseException {
        Integer slow = Integer.valueOf(request.queryParams("slow"));
        Integer fast = Integer.valueOf(request.queryParams("fast"));
        CrossMovingAverageStrategy strategy = new CrossMovingAverageStrategy(slow, fast);
        List<OperationResult> stocks = stockService.getOperationResults(strategy);
        return gson.toJson(stocks);
    }

    public static Object getMetrics(Request request, Response response) throws IOException, ParseException {
        Integer slow = Integer.valueOf(request.queryParams("slow"));
        Integer fast = Integer.valueOf(request.queryParams("fast"));
        CrossMovingAverageStrategy strategy = new CrossMovingAverageStrategy(slow, fast);
        List<Metric> metrics = stockService.getMetrics(strategy);
        return gson.toJson(metrics);
    }

}
