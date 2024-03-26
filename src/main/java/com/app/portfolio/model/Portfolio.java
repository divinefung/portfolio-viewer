package com.app.portfolio.model;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    private List<Position> positions = new ArrayList<>();

    public Portfolio(List<Position> positions) {
        this.positions = positions;
    }

    public Portfolio() {
    }


    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}
