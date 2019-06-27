package api;

import api.controller.DefaultController;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        port(8080);
        get("/", DefaultController::getData);
    }

}
