package com.app.portfolio.pubsub;

import com.app.portfolio.handler.PortfolioPrinter;
import com.app.portfolio.model.MarketDataUpdate;
import com.app.portfolio.model.Security;
import com.app.portfolio.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

@Component
public class MarketDataSubscriber {

    @Autowired
    SecurityRepository securityRepository;

    @Autowired
    protected PortfolioPrinter printer;

    private HashMap<String, Double> lastPriceMap;

    int tickCount = 1;

    @EventListener
    public void processMarketDataUpdate(MarketDataEvent marketDataEvent) {
        List<MarketDataUpdate> marketDataUpdateList = marketDataEvent.getMarketDataUpdateList();
        lastPriceMap = new HashMap<>();
        System.out.println("## " + tickCount + " Market Data Update");

        for (MarketDataUpdate marketDataUpdate : marketDataUpdateList) {
            lastPriceMap.put(marketDataUpdate.getSymbol(), marketDataUpdate.getPrice());
            Security security = securityRepository.findById(marketDataUpdate.getSymbol()).orElse(null);
            System.out.println(String.format(security.getName() + " changes to %s", NumberFormat.getInstance().format(marketDataUpdate.getPrice())));
        }

        // Print portfolio details
        try {
            printer.printPortfolioDetails(lastPriceMap);
        } catch (Exception e){
            System.err.println(String.format("Error occurred while printing portfolio details %s", e));
        }
        tickCount++;

    }
}
