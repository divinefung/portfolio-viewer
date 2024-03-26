package com.app.portfolio.handler;

import com.app.portfolio.model.Option;
import com.app.portfolio.model.Portfolio;
import com.app.portfolio.model.Position;
import com.app.portfolio.repository.SecurityRepository;
import com.app.portfolio.util.CSVReader;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PortfolioPrinter {

    @Autowired
    SecurityRepository securityRepository;

    @Autowired
    CSVReader csvReader;

    public List<Position> positions;

    Pattern optionSymbolPattern = Pattern.compile("^[A-Z]+-[A-Z]{3}-\\d{4}-\\d+-[CP]$");

    NumberFormat numberFormatter = NumberFormat.getInstance();

    public void init() throws IOException {
        positions = csvReader.readPositions();
        numberFormatter.setMaximumFractionDigits(2);
        numberFormatter.setMinimumFractionDigits(2);
    }

    private double N(double x){
        // TODO: Implement without apache commons (3rd party lib)
        NormalDistribution normalDistribution = new NormalDistribution();
        return normalDistribution.cumulativeProbability(x);
    }

    public void printPortfolioDetails(HashMap<String, Double> lastPriceMap) throws ParseException {
        System.out.println("\n## Portfolio");
        System.out.println(String.format("%-20s%20s%20s%20s", "symbol", "price", "qty", "value"));

        double totalPortfolioValue = 0;
        for (Position position : positions){

            String symbol = position.getSymbol();

            if (isEuropeanOption(symbol)){

                // Handle as European option
                String [] tokens = symbol.split("-");

                // Parse expiry date
                SimpleDateFormat sdf = new SimpleDateFormat("MMMyyyy");
                Date date = sdf.parse(tokens[1] + tokens[2]);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.getTime();

                Option option = Option.Builder.builder()
                        .symbol(symbol)
                        .underlyingSymbol(tokens[0])
                        .expiryDate(calendar.getTime())
                        .strike(Double.parseDouble(tokens[3]))
                        .isCall(tokens[4].equalsIgnoreCase("C"))
                        .build();

                LocalDate expiryDate = option.getExpiryDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long daysTillExpiry = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);

                double S = lastPriceMap.get(option.getUnderlyingSymbol());
                double K = option.getStrike();
                double r = 0.02;
                double t = daysTillExpiry / 365.00;
                double sigma = securityRepository.findById(option.getUnderlyingSymbol()).orElse(null).getStandardDeviation();
                double d1 = (Math.log(S / K) + (r + Math.pow(sigma, 2) / 2) * t) / (sigma * Math.sqrt(t));
                double d2 = d1 - sigma * Math.sqrt(t);
                double optionPrice;
                if (option.getIsCall()){
                    optionPrice = S * N(d1) - K * Math.exp(-r * t) * N(d2);
                } else {
                    optionPrice = K * Math.exp(-r * t) * N(-d2) - S * N(-d1);
                }
                double positionValue = truncateTo2DP(optionPrice) * position.getPositionSize();
                System.out.println(String.format("%-20s%20s%20s%20s",
                        position.getSymbol(),
                        numberFormatter.format(optionPrice),
                        numberFormatter.format(position.getPositionSize()),
                        numberFormatter.format(positionValue)));
                totalPortfolioValue += positionValue;

            } else {

                // Handle as common stock
                double positionValue = lastPriceMap.get(position.getSymbol()) * position.getPositionSize();
                System.out.println(String.format("%-20s%20s%20s%20s",
                        position.getSymbol(),
                        numberFormatter.format(lastPriceMap.get(position.getSymbol())),
                        numberFormatter.format(position.getPositionSize()),
                        numberFormatter.format(positionValue)));
                totalPortfolioValue += positionValue;
            }
        }

        System.out.println();
        System.out.println(String.format("%-40s%40s", "#Total Portfolio", numberFormatter.format(totalPortfolioValue)));
        System.out.println("\n");
    }

    protected double truncateTo2DP(double n){
        return Math.floor(n * 100) / 100;
    }

    protected boolean isEuropeanOption(String symbol){
        Matcher matcher = optionSymbolPattern.matcher(symbol);
        return matcher.matches();
    }

}
