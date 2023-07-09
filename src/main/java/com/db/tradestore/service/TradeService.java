package com.db.tradestore.service;


import com.db.tradestore.entity.Trade;
import com.db.tradestore.entity.TradeValidityStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TradeService{

    public TradeValidityStatus getTradeValidityStatus(Trade Trade);

    public void saveTrade(Trade trade);

    public List<Trade> findAll();

    public Optional<Trade> getTrade(String tradeId);

    public void setTradeAsExpire();

    default boolean isValidVersion (Trade newTrade, Trade oldTrade){
        if(oldTrade==null){
            return true;
        }
        else{
           return newTrade.getVersion() > oldTrade.getVersion();
        }
    }

    default boolean isMaturityDateValid(Trade trade){
        return trade.getMaturityDate().isAfter(LocalDate.now());

    }

}
