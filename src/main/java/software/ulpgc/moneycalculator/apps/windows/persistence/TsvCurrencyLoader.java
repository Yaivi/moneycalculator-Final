package software.ulpgc.moneycalculator.apps.windows.persistence;

import software.ulpgc.moneycalculator.architecture.model.Currency;
import software.ulpgc.moneycalculator.architecture.persistence.CurrencyLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TsvCurrencyLoader implements CurrencyLoader {
    private final File file;

    public TsvCurrencyLoader(File file) {
        this.file = file;
    }

    @Override
    public List<Currency> load() {
        try{
            return load(new FileReader(file));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Currency> load(FileReader file) throws IOException {
        return load(new BufferedReader(file));
    }

    private List<Currency> load(BufferedReader reader) throws IOException {
        List<Currency> currencies = new ArrayList<>();
        while(true){
            String line= reader.readLine();
            if(line==null)break;
            currencies.add(toCurrency(line));
        }
        return currencies;
    }

    private Currency toCurrency(String line) {
        return toCurrency(line.split("\t"));
    }

    private Currency toCurrency(String[] fields){
        return new Currency(
                fields[0],
                fields[1],
                fields[2]
        );
    }
}
