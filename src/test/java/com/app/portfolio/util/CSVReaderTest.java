package com.app.portfolio.util;

import com.app.portfolio.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVReaderTest {

    private CSVReader csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new CSVReader("classpath:portfolio-position-test.csv");
    }

    @Test
    void testReadPositions() throws IOException {
        List<Position> positions = csvReader.readPositions();
        assertEquals(3, positions.size());
        assertEquals("AAPL", positions.get(0).getSymbol());
        assertEquals(1000, positions.get(0).getPositionSize());
        assertEquals("AAPL-OCT-2024-170-C", positions.get(1).getSymbol());
        assertEquals(100, positions.get(1).getPositionSize());
        assertEquals("TSLA", positions.get(2).getSymbol());
        assertEquals(-500, positions.get(2).getPositionSize());
    }
}

