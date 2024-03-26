package com.app.portfolio.model;

import java.util.Date;

public class Option {

    private String symbol;
    private String underlyingSymbol;
    private Date expiryDate;
    private Double strike;
    private Boolean isCall;

    public Option() {
    }

    public Option(String symbol, String underlyingSymbol, Date expiryDate, Double strike, Boolean isCall) {
        this.symbol = symbol;
        this.underlyingSymbol = underlyingSymbol;
        this.expiryDate = expiryDate;
        this.strike = strike;
        this.isCall = isCall;
    }

    private Option(Builder builder) {
        setSymbol(builder.symbol);
        setUnderlyingSymbol(builder.underlyingSymbol);
        setExpiryDate(builder.expiryDate);
        setStrike(builder.strike);
        setIsCall(builder.isCall);
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getUnderlyingSymbol() {
        return underlyingSymbol;
    }

    public void setUnderlyingSymbol(String underlyingSymbol) {
        this.underlyingSymbol = underlyingSymbol;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getStrike() {
        return strike;
    }

    public void setStrike(Double strike) {
        this.strike = strike;
    }

    public Boolean getIsCall() {
        return isCall;
    }

    public void setIsCall(Boolean call) {
        isCall = call;
    }


    public static final class Builder {
        private String symbol;
        private String underlyingSymbol;
        private Date expiryDate;
        private Double strike;
        private Boolean isCall;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder symbol(String val) {
            symbol = val;
            return this;
        }

        public Builder underlyingSymbol(String val) {
            underlyingSymbol = val;
            return this;
        }

        public Builder expiryDate(Date val) {
            expiryDate = val;
            return this;
        }

        public Builder strike(Double val) {
            strike = val;
            return this;
        }

        public Builder isCall(Boolean val) {
            isCall = val;
            return this;
        }

        public Option build() {
            return new Option(this);
        }
    }
}
