package com.app.portfolio.util;

import java.util.List;


public interface MockMarketDataProvider {

    public void generateAndPublishMarketDataUpdate(List<String> symbolsList);

}
