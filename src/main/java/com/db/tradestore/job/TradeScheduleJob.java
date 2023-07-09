package com.db.tradestore.job;

import com.db.tradestore.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

public class TradeScheduleJob {
    private static final Logger logger = LoggerFactory.getLogger(TradeScheduleJob.class);

    @Autowired
    private TradeService tradeService;
    @Scheduled(fixedDelay  = 100)
    public void setTradeExpiry(){
        logger.info("Job triggered at{}", new Date());
        tradeService.setTradeAsExpire();
    }
}
