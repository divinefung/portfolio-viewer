package com.app.portfolio.util;

import com.app.portfolio.pubsub.MarketDataPublisher;
import com.app.portfolio.model.MarketDataUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// @Component
public class RandomizedMarketDataProvider implements MockMarketDataProvider {

    private final MarketDataPublisher marketDataPublisher;

    // @Autowired
    public RandomizedMarketDataProvider(MarketDataPublisher marketDataPublisher) {
        this.marketDataPublisher = marketDataPublisher;
    }

    public void generateAndPublishMarketDataUpdate(List<String> symbolsList) {

        List<MarketDataUpdate> marketDataUpdateList = new ArrayList<>();
        for (String symbol : symbolsList) {
            double price = new Random().nextDouble() * 1000; // Adjust range as needed
            MarketDataUpdate marketDataUpdate = new MarketDataUpdate(symbol, price);
            marketDataUpdateList.add(marketDataUpdate);
        }
        marketDataPublisher.publishMarketDataUpdate(marketDataUpdateList);

    }


}
