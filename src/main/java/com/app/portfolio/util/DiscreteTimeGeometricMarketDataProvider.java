package com.app.portfolio.util;

import com.app.portfolio.pubsub.MarketDataPublisher;
import com.app.portfolio.model.MarketDataUpdate;
import com.app.portfolio.model.Security;
import com.app.portfolio.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class DiscreteTimeGeometricMarketDataProvider implements MockMarketDataProvider {

    @Value("${interval}")
    private int interval;

    @Autowired
    private MarketDataPublisher marketDataPublisher;

    @Autowired
    private SecurityRepository securityRepository;

    private Map<String, Double> initialPriceMap = new HashMap<String, Double>() {{
        put("AAPL", 170.00);
        put("TSLA", 170.00);
        put("MSFT", 430.00);
        put("MS", 90.00);
    }};

    protected Map<String, Double> previousPriceMap = new HashMap<>();

    public DiscreteTimeGeometricMarketDataProvider(MarketDataPublisher marketDataPublisher, SecurityRepository securityRepository) {
        this.marketDataPublisher = marketDataPublisher;
        this.securityRepository = securityRepository;
    }

    public void generateAndPublishMarketDataUpdate(List<String> symbolsList) {

        List<MarketDataUpdate> marketDataUpdateList = new ArrayList<>();
        for (String symbol : symbolsList) {
            double previousPrice = previousPriceMap.get(symbol) == null? initialPriceMap.get(symbol): previousPriceMap.get(symbol);
            double price = calculateNextPrice(symbol, previousPrice);
            MarketDataUpdate marketDataUpdate = new MarketDataUpdate(symbol, price);
            marketDataUpdateList.add(marketDataUpdate);
            previousPriceMap.put(symbol, price);
        }
        marketDataPublisher.publishMarketDataUpdate(marketDataUpdateList);
    }

    public double calculateNextPrice(String symbol, double previousPrice){

        Security security = securityRepository.findById(symbol).orElse(null);
        double epsilon = new Random().nextGaussian();
        double mu = security.getExpectedReturn();
        double sigma = security.getStandardDeviation();
        double dt = interval;

        double delta = ((mu * dt / 7257600) + (sigma * epsilon * Math.sqrt(dt / 7257600))) * previousPrice;
        double newPrice = previousPrice + delta;

        return (newPrice >= 0)? truncateTo2DP(newPrice) : 0;
    }

    protected double truncateTo2DP(double n){
        return Math.floor(n * 100) / 100;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

}
