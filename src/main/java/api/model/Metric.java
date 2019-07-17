package api.model;

public class Metric {
    public String strategyName;
    public Integer wins;
    public Integer loss;
    public Integer totalOperations;
    public OperationResult bestMovement;
    public OperationResult worstMovement;

    public Metric() { }
}
