package com.db.tradestore.entity;

public enum TradeValidityStatus {


    OK("Valid Trade"),
    INVALID_MATURITY_DATE("Invalid Maturity Date"),
    OBSELETE_VERSION("Old Version, newer version exists"),
    INVALID_ID_FORMAT("Invalid Trade ID format, Trade ID should start with 'T'");


    private final String message;

    TradeValidityStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
