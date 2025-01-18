package software.ulpgc.moneycalculator.apps.windows.persistence;

import software.ulpgc.moneycalculator.architecture.model.Currency;
import software.ulpgc.moneycalculator.architecture.model.ExchangeRate;
import software.ulpgc.moneycalculator.architecture.persistence.ExchangeRateLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TsvExchangeRateLoader implements ExchangeRateLoader {
    private final File file;

    public TsvExchangeRateLoader(File file) {
        this.file = file;
    }


    @Override
    public List<ExchangeRate> load() {
        try{
            return loadExchanges(new FileReader(file));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private List<ExchangeRate> loadExchanges(FileReader file) throws IOException {
        return loadExchanges(new BufferedReader(file));
    }

    private List<ExchangeRate> loadExchanges(BufferedReader reader) throws IOException {
        List<ExchangeRate>exchangeRates=new ArrayList<>();
        while(true){
            String line = reader.readLine();
            if(line==null) break;
            exchangeRates.add(toExchangeRate(line));
        }
        return exchangeRates;
    }

    private ExchangeRate toExchangeRate(String line) {
        return toExchangeRate(line.split("\t"));
    }
    private ExchangeRate toExchangeRate(String[] fields) {
        return new ExchangeRate(
                new Currency(fields[0],fields[1], fields[2]),
                new Currency(fields[3],fields[4], fields[5]),
                toDouble(fields[6]),
                toLocalDate(fields[7])
        );
    }

    private double toDouble(String text) {
        return Double.parseDouble(text);
    }
    private LocalDate toLocalDate(String text) {
        return LocalDate.parse(text);
    }

}
