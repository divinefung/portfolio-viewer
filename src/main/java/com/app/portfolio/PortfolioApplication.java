package com.app.portfolio;

import com.app.portfolio.handler.PortfolioPrinter;
import com.app.portfolio.model.Security;
import com.app.portfolio.repository.SecurityRepository;
import com.app.portfolio.util.MockMarketDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

@SpringBootApplication
public class PortfolioApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }

    @Value("${interval}")
    private int interval;

    @Autowired
    SecurityRepository securityRepository;
    @Autowired
    MockMarketDataProvider mockMarketDataProvider;

    @Autowired
    PortfolioPrinter printer;

    @Override
    public void run(String... args) throws Exception {

        Security security = Security.Builder.builder()
                .ticker("AAPL")
                .name("Apple Inc")
                .exchange("NYSE")
                .expectedReturn(0.26)
                .standardDeviation(0.2251)
                .build();
        securityRepository.save(security);

        Security security2 = Security.Builder.builder()
                .ticker("TSLA")
                .name("Tesla Inc")
                .exchange("NYSE")
                .expectedReturn(0.2785)
                .standardDeviation(0.5980)
                .build();
        securityRepository.save(security2);

        Security security3 = Security.Builder.builder()
                .ticker("MSFT")
                .name("Microsoft Corp")
                .exchange("NYSE")
                .expectedReturn(0.2884)
                .standardDeviation(0.2029)
                .build();
        securityRepository.save(security3);

        Security security4 = Security.Builder.builder()
                .ticker("MS")
                .name("Morgan Stanley")
                .exchange("NYSE")
                .expectedReturn(0.1373)
                .standardDeviation(0.27)
                .build();
        securityRepository.save(security4);

        List<String> symbols = new ArrayList<>();
        for (Security s : securityRepository.findAll()) {
            symbols.add(s.getTicker());
        }

        printer.init();

        while (true) {
            mockMarketDataProvider.generateAndPublishMarketDataUpdate(symbols);
            sleep(interval * 1000);
        }

    }
}
