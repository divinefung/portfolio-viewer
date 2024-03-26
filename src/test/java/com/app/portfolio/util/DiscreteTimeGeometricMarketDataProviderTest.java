package com.app.portfolio.util;

import com.app.portfolio.model.MarketDataUpdate;
import com.app.portfolio.model.Security;
import com.app.portfolio.pubsub.MarketDataPublisher;
import com.app.portfolio.repository.SecurityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DiscreteTimeGeometricMarketDataProviderTest {

    DiscreteTimeGeometricMarketDataProvider unit;

    @Mock
    SecurityRepository securityRepository;

    @Mock
    MarketDataPublisher marketDataPublisher;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        unit = new DiscreteTimeGeometricMarketDataProvider(marketDataPublisher, securityRepository);
        unit.setInterval(2);

        Security security = Security.Builder.builder()
                .ticker("AAPL")
                .name("Apple Inc")
                .exchange("NYSE")
                .expectedReturn(0.26)
                .standardDeviation(0.2251)
                .build();

        Security security2 = Security.Builder.builder()
                .ticker("TSLA")
                .name("Tesla Inc")
                .exchange("NYSE")
                .expectedReturn(0.2785)
                .standardDeviation(0.5980)
                .build();

        Security security3 = Security.Builder.builder()
                .ticker("MSFT")
                .name("Microsoft Corp")
                .exchange("NYSE")
                .expectedReturn(100000)
                .standardDeviation(0)
                .build();

        when(securityRepository.findById("AAPL")).thenReturn(Optional.of(security));
        when(securityRepository.findById("TSLA")).thenReturn(Optional.of(security2));
        when(securityRepository.findById("MSFT")).thenReturn(Optional.of(security3));

    }

    @Test
    public void shouldPublishMarketDataUpdate(){

        unit.generateAndPublishMarketDataUpdate(Arrays.asList("AAPL", "TSLA"));
        verify(marketDataPublisher, times(1)).publishMarketDataUpdate(anyList());
    }

    @Test
    public void shouldCalculatePrice(){

        unit.generateAndPublishMarketDataUpdate(Arrays.asList("MSFT"));
        ArgumentCaptor<List<MarketDataUpdate>> captor = ArgumentCaptor.forClass(List.class);
        verify(marketDataPublisher, times(1)).publishMarketDataUpdate(captor.capture());
        List<MarketDataUpdate> update = captor.getValue();
        assertEquals(1, update.size());
        assertEquals("MSFT", update.get(0).getSymbol());
        assertEquals(441.849, update.get(0).getPrice(), 0.01);

    }

}
