package com.app.portfolio.util;

import com.app.portfolio.model.Position;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class CSVReader {

    String path;

    public CSVReader() {
    }

    public CSVReader(String path) {
        this.path = path;
    }

    public List<Position> readPositions() throws IOException {
        List<Position> positions = new ArrayList<>();
        File file = ResourceUtils.getFile(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine(); // read first line
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String symbol = parts[0].trim();
                    int positionSize = Integer.parseInt(parts[1].trim());
                    positions.add(new Position(symbol, positionSize));
                }
            }
        }
        return positions;
    }
}
