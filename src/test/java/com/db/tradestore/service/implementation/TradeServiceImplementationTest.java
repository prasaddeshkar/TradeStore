package com.db.tradestore.service.implementation;

import com.db.tradestore.TradeStoreApplication;
import com.db.tradestore.entity.Trade;
import com.db.tradestore.entity.TradeValidityStatus;
import com.db.tradestore.service.TradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringJUnitConfig(TradeStoreApplication.class)
@SpringBootTest(args = "--spring.application.run.mode=DEV")
@ExtendWith(MockitoExtension.class)
class TradeServiceImplementationTest {


    @SpyBean
    private TradeService tradeService;


    @Test
    void isValidVersion_no_old_trade() {
        Trade newtrade= new Trade("T" + Math.random(),3,"CP-2", "B1", LocalDate.of(2024, 10,10),
                LocalDate.now(), 'N');

        assertTrue(tradeService.isValidVersion(newtrade, null));
    }
    @Test
    void isValidVersion_true() {
        Trade oldTrade= new Trade("T" + Math.random(),2,"CP-2", "B1", LocalDate.of(2024, 10,10),
                LocalDate.now(), 'N');
        Trade newtrade= new Trade("T" + Math.random(),3,"CP-2", "B1", LocalDate.of(2024, 10,10),
                LocalDate.now(), 'N');
        assertTrue(tradeService.isValidVersion(newtrade, oldTrade));
    }

    @Test
    void isValidTradeID_true() {
        Trade trade= new Trade("T" + Math.random(),2,"CP-2", "B1", LocalDate.of(2024, 10,10),
                LocalDate.now(), 'N');
        assertTrue(tradeService.validateTadeName(trade.getTradeId()));
    }

    @Test
    void isValidTradeID_false() {
        Trade trade= new Trade("X" + Math.random(),2,"CP-2", "B1", LocalDate.of(2024, 10,10),
                LocalDate.now(), 'N');
        assertFalse(tradeService.validateTadeName(trade.getTradeId()));
    }

    @Test
    void isValidVersion_false() {
        Trade oldTrade= new Trade("T" + Math.random(),2,"CP-2", "B1", LocalDate.of(2024, 10,10),
                LocalDate.now(), 'N');
        Trade newtrade= new Trade("T" + Math.random(),2,"CP-2", "B1", LocalDate.of(2024, 10,10),
                LocalDate.now(), 'N');
        assertFalse(tradeService.isValidVersion(newtrade, oldTrade));
    }

    @Test
    void isMaturityDateValid_true() {
        Trade trade= new Trade("T" + Math.random(),2,"CP-2", "B1", LocalDate.of(2024, 10,10),
                LocalDate.now(), 'N');
        assertTrue(tradeService.isMaturityDateValid(trade));
    }

    @Test
    void isMaturityDateValid_false() {
        Trade trade= new Trade("T" + Math.random(),2,"CP-2", "B1", LocalDate.of(2021, 10,10),
                LocalDate.now(), 'N');
        assertFalse(tradeService.isMaturityDateValid(trade));
    }

    @Test
    void getTradeValidityStatus_OK() {
       Trade trade= new Trade("T" + Math.random(),2,"CP-2", "B1", LocalDate.of(2024, 10,10),
                LocalDate.now(), 'N');
      assertSame(TradeValidityStatus.OK,tradeService.getTradeValidityStatus(trade) );

    }

    @Test
    void getTradeValidityStatus_INVALID_MATURITY_DATE() {
        Trade trade= new Trade("T" + Math.random(),2,"CP-2", "B1", LocalDate.of(2021, 10,10),
                LocalDate.now(), 'N');
        assertSame(TradeValidityStatus.INVALID_MATURITY_DATE,tradeService.getTradeValidityStatus(trade) );

    }

    @Test
    void getTradeValidityStatus_OBSELETE_VERSION() {
        Trade trade= new Trade("T" + Math.random(),2,"CP-2", "B1", LocalDate.of(2024, 10,10),
                LocalDate.now(), 'N');
        tradeService.saveTrade(trade);
        trade.setVersion(1);
        assertSame(TradeValidityStatus.OBSELETE_VERSION,tradeService.getTradeValidityStatus(trade) );

    }
    @Test
    void saveTrade() {
        Trade trade= new Trade("T" + Math.random(),2,"CP-2", "B1", LocalDate.of(2021, 10,10),
                LocalDate.now(), 'N');
        tradeService.saveTrade(trade);
        assertDoesNotThrow(()-> tradeService.saveTrade(trade)) ;

    }

    @Test
    void findAll() {
        assertTrue(tradeService.findAll().size() >0);
    }

       @Test
    void getTrade() {

           assertTrue(tradeService.getTrade(tradeService.findAll().stream().findFirst().get().getTradeId()).isPresent());
    }

    @Test
    void setTradeAsExpire() {
        assertDoesNotThrow(()-> tradeService.setTradeAsExpire()) ;
    }
}