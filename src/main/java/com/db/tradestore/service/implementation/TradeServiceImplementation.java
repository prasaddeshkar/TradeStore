package com.db.tradestore.service.implementation;

import com.db.tradestore.entity.Trade;
import com.db.tradestore.entity.TradeValidityStatus;
import com.db.tradestore.repository.TradeStoreRepository;
import com.db.tradestore.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImplementation implements TradeService {

    @Autowired
    private TradeStoreRepository tradeStoreRepository;
    /**
     * @param trade
     * @return
     */
    @Override
    public TradeValidityStatus getTradeValidityStatus (Trade trade) {
        if(trade== null) throw new IllegalArgumentException("Trade object is null");
        
        if(! isMaturityDateValid(trade)){
            return TradeValidityStatus.INVALID_MATURITY_DATE;
        } else if (! isValidVersion(trade,getPreviousTrade(trade.getTradeId()))) {
            return  TradeValidityStatus.OBSELETE_VERSION;
        } else
            return TradeValidityStatus.OK;
    }



    /**
     * @param trade
     */
    @Override
    public void saveTrade(Trade trade) {
        trade.setCreatedDate(LocalDate.now());
        tradeStoreRepository.save(trade);
    }

    /**
     * @return
     */

    @Override
    public List<Trade> findAll() {
        return tradeStoreRepository.findAll();
    }

    /**
     * @param tradeId
     * @return
     */
    @Override
    public Optional<Trade> getTrade(String tradeId) {
        return tradeStoreRepository.findById(tradeId);
    }



    @Override
    public void setTradeAsExpire() {
        tradeStoreRepository.findAll().forEach(t -> {
               t.setExpired(isMaturityDateValid(t) ? 'Y' :'N');
               tradeStoreRepository.save(t);
                } );
    }

    private Trade getPreviousTrade(String tradeId){
       Optional<Trade> previousTrade=
               getTrade(tradeId).stream().
                       sorted(Comparator.comparingInt(Trade::getVersion).reversed()).limit(1).findFirst();
        return previousTrade.orElse(null);

    }
}
