package software.ulpgc.moneycalculator.apps.windows;


import software.ulpgc.moneycalculator.apps.windows.persistence.TsvCurrencyLoader;
import software.ulpgc.moneycalculator.apps.windows.persistence.TsvExchangeRateLoader;
import software.ulpgc.moneycalculator.architecture.control.ExchangeCommand;
import software.ulpgc.moneycalculator.architecture.model.Currency;
import software.ulpgc.moneycalculator.architecture.model.ExchangeRate;
import software.ulpgc.moneycalculator.architecture.persistence.CurrencyLoader;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TsvExchangeRateLoader loadExchangeRates = new TsvExchangeRateLoader(new File("./moneycalculator/src/main/resources/ExchangeRate.tsv"));
        List<ExchangeRate> exchangeRates = loadExchangeRates.load();
        CurrencyLoader loadCurrencies = new TsvCurrencyLoader(new File("./moneycalculator/src/main/resources/Currencies.tsv"));
        List<Currency> currencies = loadCurrencies.load();
        MainFrame mainFrame = new MainFrame(currencies);

        ExchangeCommand exchangeCommand = new ExchangeCommand(
                mainFrame.moneyDialog(),
                mainFrame.currencyDialog(),
                mainFrame.moneyDisplay(),
                exchangeRates
        );


        mainFrame.add("exchange", exchangeCommand);
        mainFrame.setVisible(true);


    }
}
