package com.app.portfolio.pubsub;

import com.app.portfolio.model.MarketDataUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarketDataPublisher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void publishMarketDataUpdate(List<MarketDataUpdate> marketDataUpdate) {
        eventPublisher.publishEvent(new MarketDataEvent(marketDataUpdate));
    }
}
