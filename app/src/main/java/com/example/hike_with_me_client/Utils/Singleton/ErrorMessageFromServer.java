package com.example.hike_with_me_client.Utils.Singleton;

public class ErrorMessageFromServer {

    private static ErrorMessageFromServer instance = null;
    private String errorMessageFromServer;

    public ErrorMessageFromServer() {

    }

    public static void initErrorMessageFromServer() {
        if (instance == null) {
            instance = new ErrorMessageFromServer();
        }
    }

    public static ErrorMessageFromServer getInstance() {
        return instance;
    }

    public String getErrorMessageFromServer() {
        return errorMessageFromServer;
    }

    public void setErrorMessageFromServer(String errorMessageFromServer) {
        this.errorMessageFromServer = errorMessageFromServer;
    }
}
