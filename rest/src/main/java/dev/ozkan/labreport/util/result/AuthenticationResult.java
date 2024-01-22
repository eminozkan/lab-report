package dev.ozkan.labreport.util.result;

public class AuthenticationResult {
    private OperationResult result;
    private OperationFailureReason reason;
    private String message;
    private String accessToken;

    private AuthenticationResult(){}

    public static AuthenticationResult success(String accessToken){
        return new AuthenticationResult()
                .setResult(OperationResult.SUCCESS)
                .setAccessToken(accessToken);
    }

    public static AuthenticationResult failed(OperationFailureReason reason, String message){
        return new AuthenticationResult()
                .setResult(OperationResult.FAILED)
                .setReason(reason)
                .setMessage(message);
    }


    public boolean isSuccess() {
        return result.equals(OperationResult.SUCCESS);
    }

    public AuthenticationResult setResult(OperationResult result) {
        this.result = result;
        return this;
    }

    public OperationFailureReason getReason() {
        return reason;
    }

    public AuthenticationResult setReason(OperationFailureReason reason) {
        this.reason = reason;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AuthenticationResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public AuthenticationResult setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
}
