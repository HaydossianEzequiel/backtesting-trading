package api;

import api.controller.DefaultController;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        port(8080);
        get("/frontend", DefaultController::getFrontend);
        get("/", DefaultController::getData);
        get("/result", DefaultController::getOperationResults);
        get("/metrics", DefaultController::getMetrics);
        get("/moving_average_metrics", DefaultController::getMovingAverageMetrics);

    }

}
