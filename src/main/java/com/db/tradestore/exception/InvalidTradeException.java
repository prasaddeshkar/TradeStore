package com.db.tradestore.exception;

public class InvalidTradeException extends RuntimeException{

    private static final long serialVersionUID=135L;
    public InvalidTradeException(String tradeId, String message){
        super(String.format("Invalid Trade with Trade ID: %s. Reason: %s", tradeId, message));
    }
}
