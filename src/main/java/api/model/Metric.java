package api.model;

public class Metric {
    public String strategyName;
    public Integer wins;
    public Integer loss;
    public Integer totalOperations;

    public Metric(String strategyName, Integer wins, Integer loss, Integer totalOperations) {
        this.loss = loss;
        this.wins = wins;
        this.totalOperations = totalOperations;
        this.strategyName = strategyName;
    }
}
