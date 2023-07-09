package com.db.tradestore.repository;

import com.db.tradestore.entity.Trade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeStoreRepository extends MongoRepository<Trade, String> {
}
