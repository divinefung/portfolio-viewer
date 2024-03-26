package com.app.portfolio.pubsub;

import com.app.portfolio.model.MarketDataUpdate;
import java.util.List;

public class MarketDataEvent {

    private List<MarketDataUpdate> marketDataUpdateList;

    public MarketDataEvent(List<MarketDataUpdate> marketDataUpdateList) {
        this.marketDataUpdateList = marketDataUpdateList;
    }

    public List<MarketDataUpdate> getMarketDataUpdateList() {
        return marketDataUpdateList;
    }
}
