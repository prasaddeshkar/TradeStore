package com.db.tradestore.controller;

import com.db.tradestore.entity.Trade;
import com.db.tradestore.entity.TradeValidityStatus;
import com.db.tradestore.exception.InvalidTradeException;
import com.db.tradestore.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController

public class TradeStoreController {
    @Autowired
    private TradeService tradeService;



    @PostMapping("/trade")
    @Operation(summary = "Create a Trade")
    @ApiResponse(responseCode = "200", description = "Creates a Trade",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TradeValidityStatus.class)) })

    @ApiResponse(responseCode = "400", description = "Bad Request",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TradeValidityStatus.class)) })
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TradeValidityStatus.class)) })
     public ResponseEntity<TradeValidityStatus> saveTrade(@RequestBody Trade trade){
        try {
            TradeValidityStatus tradeStatus = tradeService.getTradeValidityStatus(trade);
            if (TradeValidityStatus.OK.equals(tradeStatus)) {
                tradeService.saveTrade(trade);
               return ResponseEntity.ok().body(TradeValidityStatus.OK);
            } else {
                throw new InvalidTradeException(trade.getTradeId(), tradeStatus.getMessage());
            }
        } catch (InvalidTradeException exp){
            throw exp;
        } catch (HttpClientErrorException.BadRequest exp){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException exp) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @Operation(summary = "List all Trades")
    @ApiResponse(responseCode = "200", description = "List all Trades",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TradeValidityStatus.class)) })

    @ApiResponse(responseCode = "400", description = "Bad Request",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TradeValidityStatus.class)) })
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TradeValidityStatus.class)) })
    public ResponseEntity<List<Trade>> getAllTrades() {
        try {
            return ResponseEntity.ok().body(tradeService.findAll());

        } catch (HttpClientErrorException.BadRequest exp){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException exp) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
