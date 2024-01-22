package dev.ozkan.labreport.util.result;

public class CrudResult {
    private OperationResult result;
    private OperationFailureReason reason;
    private String message;

    private CrudResult(){}

    public static CrudResult success(){
        return new CrudResult()
                .setResult(OperationResult.SUCCESS);
    }

    public static CrudResult failed(OperationFailureReason reason, String message){
        return new CrudResult()
                .setResult(OperationResult.FAILED)
                .setReason(reason)
                .setMessage(message);
    }

    public boolean isSuccess() {
        return result.equals(OperationResult.SUCCESS);
    }

    public OperationFailureReason getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }

    private CrudResult setResult(OperationResult result) {
        this.result = result;
        return this;
    }

    private CrudResult setReason(OperationFailureReason reason) {
        this.reason = reason;
        return this;
    }

    private CrudResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
