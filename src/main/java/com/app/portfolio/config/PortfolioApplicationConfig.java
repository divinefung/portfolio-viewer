package com.app.portfolio.config;

import com.app.portfolio.util.CSVReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortfolioApplicationConfig {

    @Bean
    public CSVReader csvReader(){
        return new CSVReader("classpath:portfolio-position.csv");
    }

}
