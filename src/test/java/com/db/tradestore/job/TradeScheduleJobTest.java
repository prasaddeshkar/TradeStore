package com.db.tradestore.job;

import com.db.tradestore.TradeStoreApplication;
import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig(TradeStoreApplication.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(args = "--spring.application.run.mode=DEV")
class TradeScheduleJobTest {

    @SpyBean
    private TradeScheduleJob tradeScheduleJob;
    @Test
    void setTradeExpiry() {
        await()
                .atMost(Durations.ONE_MINUTE)
                .untilAsserted(() -> verify(tradeScheduleJob,
                        atLeast(3)));
    }
}