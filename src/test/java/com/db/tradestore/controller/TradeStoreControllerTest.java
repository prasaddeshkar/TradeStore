package com.db.tradestore.controller;

import com.db.tradestore.TradeStoreApplication;
import com.db.tradestore.entity.Trade;
import com.db.tradestore.entity.TradeValidityStatus;
import com.db.tradestore.exception.InvalidTradeException;
import com.db.tradestore.service.TradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TradeStoreApplication.class)
@SpringBootTest(args = "--spring.application.run.mode=DEV")
@ExtendWith(MockitoExtension.class)

class TradeStoreControllerTest {

    @SpyBean
    TradeService tradeService;

    @SpyBean
    TradeStoreController tradeStoreController;

    @Test
    void testSaveTrade_OK(){
        Trade trade = new Trade("T" + Math.random(),2,"CP-2", "B1", LocalDate.of(2024, 10,10),
                LocalDate.now(), 'N');
        ResponseEntity<TradeValidityStatus> tradeValidityStatusResponse = tradeStoreController.saveTrade(trade);
        ResponseEntity<TradeValidityStatus> tradeValidityStatusMock = new ResponseEntity<>(TradeValidityStatus.OK, HttpStatus.OK);
        assertEquals(tradeValidityStatusMock,tradeValidityStatusResponse);
    }

    @Test
    void testSaveTrade_OK_new_version(){
        Trade trade = new Trade("T" + Math.random(),2,"CP-2", "B1", LocalDate.of(2024, 10,10),
                LocalDate.now(), 'N');
        tradeStoreController.saveTrade(trade);
        trade.setVersion(trade.getVersion()+1);
        ResponseEntity<TradeValidityStatus> tradeValidityStatusResponse = tradeStoreController.saveTrade(trade);
        ResponseEntity<TradeValidityStatus> tradeValidityStatusMock = new ResponseEntity<>(TradeValidityStatus.OK, HttpStatus.OK);
        assertEquals(tradeValidityStatusMock,tradeValidityStatusResponse);
    }
    @Test
    void testSaveTrade_INVALID_MATURITY_DATE(){
        Trade trade = new Trade("T7",2,"CP-2", "B1", LocalDate.of(2021, 10,10),
                LocalDate.now(), 'N');
        assertThrows(InvalidTradeException.class,()-> tradeStoreController.saveTrade(trade));

    }
    @Test
    void testSaveTrade_OBSELETE_VERSION(){
        Trade trade = new Trade("T"+ + Math.random(),2,"CP-2", "B1", LocalDate.of(2028, 10,10),
                LocalDate.now(), 'N');
        ResponseEntity<TradeValidityStatus> tradeValidityStatusResponse = tradeStoreController.saveTrade(trade);
        ResponseEntity<TradeValidityStatus> tradeValidityStatusMock = new ResponseEntity<>(TradeValidityStatus.OK, HttpStatus.OK);
        assertEquals(tradeValidityStatusMock,tradeValidityStatusResponse);
        assertThrows(InvalidTradeException.class,()-> tradeStoreController.saveTrade(trade));
    }

    @Test
    void testgetAllTrades(){
        assertTrue(tradeStoreController.getAllTrades().getBody().size() > 0);
    }



}