package com.app.portfolio.util;

import com.app.portfolio.pubsub.MarketDataPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RandomizedMarketDataProviderTest {

    RandomizedMarketDataProvider unit;

    @Mock
    MarketDataPublisher marketDataPublisher;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        unit = new RandomizedMarketDataProvider(marketDataPublisher);
    }

    @Test
    public void shouldPublishMarketDataUpdate(){
        unit.generateAndPublishMarketDataUpdate(Arrays.asList("AAPL", "TSLA"));
        verify(marketDataPublisher, times(1)).publishMarketDataUpdate(anyList());
    }

}
