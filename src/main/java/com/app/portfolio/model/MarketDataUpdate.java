package com.app.portfolio.model;

public class MarketDataUpdate {
    private String symbol;
    private double price;

    public MarketDataUpdate(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    @Override
    public String toString() {
        return "MarketDataUpdate{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
