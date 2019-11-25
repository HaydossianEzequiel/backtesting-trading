package api.controller;

import api.model.DataStock;
import api.model.Metric;
import api.model.OperationResult;
import api.model.strategies.CrossMovingAverageBuyStrategy;
import api.model.strategies.MovingAverageBuyStrategy;
import api.model.strategies.Strategy;
import api.model.strategies.TrailingStopSellStrategy;
import api.services.StockService;
import com.google.gson.Gson;
import frontend.FrontendSource;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DefaultController {

    private static Gson gson = new Gson();
    private static StockService stockService = new StockService();

    public static Object getFrontend(Request request, Response response) throws IOException, ParseException {

        List<DataStock> stocks = stockService.getStocks();
        List<String> fechas = new ArrayList();
        List<String> values = new ArrayList();

        for (int i = 0; i < stocks.size(); i++) {
            DataStock item = stocks.get(i);
            fechas.add('"' + item.date + '"');
            String itemClose = item.close.replace(",", ".");
            values.add(itemClose);
            if (i > 1000) {
                break;
            }

        }


        String html = getHtml();
        html = html.replace("fechas", fechas.toString());
        html = html.replace("firstDataSet", values.toString());
        html = html.replace("secondDataSet", "[]");
        return html;
    }

    private static String getHtml() {
        return new FrontendSource().get();
    }

    public static Object getData(Request request, Response response) throws IOException, ParseException {

        List<DataStock> stocks = stockService.getStocks();
        return gson.toJson(stocks);
    }

    public static Object getOperationResults(Request request, Response response) throws IOException, ParseException {
        Integer slow = Integer.valueOf(request.queryParams("slow"));
        Integer fast = Integer.valueOf(request.queryParams("fast"));
        Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(slow, fast), new TrailingStopSellStrategy(8, 15, 8));
        //Strategy strategy = new Strategy(new MovingAverageBuyStrategy(slow), new TrailingStopSellStrategy(8, 15, 10));

        List<OperationResult> stocks = stockService.getOperationResults(strategy);
        return gson.toJson(stocks);
    }

    public static Object getMetrics(Request request, Response response) throws IOException, ParseException {
        Integer slow = Integer.valueOf(request.queryParams("slow"));
        Integer fast = Integer.valueOf(request.queryParams("fast"));
        Strategy strategy = new Strategy(new CrossMovingAverageBuyStrategy(slow, fast), new TrailingStopSellStrategy(8, 15, 8));
        //Strategy strategy = new Strategy(new MovingAverageBuyStrategy(slow), new TrailingStopSellStrategy(8, 15, 10));
        Metric metric = stockService.getMetrics(strategy);
        return gson.toJson(metric);
    }

    public static Object getMovingAverageMetrics(Request request, Response response) throws IOException, ParseException {

        List<Metric> metrics = stockService.getBestMovingAverageMetrics();
        return gson.toJson(metrics);
    }

}
