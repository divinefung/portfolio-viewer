package com.app.portfolio.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Security {

    @Id
    private String ticker;
    private String name;
    private String exchange;
    private double expectedReturn;
    private double standardDeviation;

    private Security(Builder builder) {
        setTicker(builder.ticker);
        setName(builder.name);
        setExchange(builder.exchange);
        setExpectedReturn(builder.expectedReturn);
        setStandardDeviation(builder.standardDeviation);
    }

    public Security() {
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public double getExpectedReturn() {
        return expectedReturn;
    }

    public void setExpectedReturn(double expectedReturn) {
        this.expectedReturn = expectedReturn;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public static final class Builder {
        private String ticker;
        private String name;
        private String exchange;
        private double expectedReturn;
        private double standardDeviation;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder ticker(String val) {
            ticker = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder exchange(String val) {
            exchange = val;
            return this;
        }

        public Builder expectedReturn(double val) {
            expectedReturn = val;
            return this;
        }

        public Builder standardDeviation(double val) {
            standardDeviation = val;
            return this;
        }

        public Security build() {
            return new Security(this);
        }
    }
}
