package api.model;

public class OperationResult {
    public String result;
    public String buyDate;
    public String sellDate;
    public Double movement;
    public Double initialPrice;
    public Double finalPrice;

    public Double getMovement() {
        return this.movement;
    }
}
