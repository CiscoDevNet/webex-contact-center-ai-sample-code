package com.cisco.wccai.grpc.server.interceptors;

public class ConnectivityException extends RuntimeException {
    private final String errorMessage;
    private final Throwable t;

    public ConnectivityException(String errorMessage, Throwable t){
        this.errorMessage = errorMessage;
        this.t = t;
    }


    @Override
    public synchronized Throwable getCause() {
        if(t!=null) return t;
        return super.getCause();
    }

    @Override
    public String getMessage() {
        if(errorMessage!=null) return errorMessage;
        return "Not able to connect to Redis" + super.getMessage();
    }
}
