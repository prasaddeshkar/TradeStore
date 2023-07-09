package com.db.tradestore;

import com.db.tradestore.controller.TradeStoreController;
import com.db.tradestore.entity.Trade;
import com.db.tradestore.entity.TradeValidityStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TradeStoreApplicationTests {

    @SpyBean
    private TradeStoreController tradeStoreController;

    @Test
    void testTradeStore() {
        Trade trade = new Trade("T" + Math.random(), 2, "CP-2", "B1", LocalDate.of(2024, 10, 10),
                LocalDate.now(), 'N');
        ResponseEntity<TradeValidityStatus> tradeValidityStatusResponse = tradeStoreController.saveTrade(trade);
        ResponseEntity<TradeValidityStatus> tradeValidityStatusMock = new ResponseEntity<>(TradeValidityStatus.OK, HttpStatus.OK);
        assertEquals(tradeValidityStatusMock, tradeValidityStatusResponse);
    }

}
