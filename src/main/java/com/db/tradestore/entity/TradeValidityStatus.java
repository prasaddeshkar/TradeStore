package com.db.tradestore.entity;

public enum TradeValidityStatus {


    OK("Valid Trade"),
    INVALID_MATURITY_DATE("Invalid Maturity Date"),
    OBSELETE_VERSION("Old Version, newer version exists");


    private final String message;

    private TradeValidityStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
