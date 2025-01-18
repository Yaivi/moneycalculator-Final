package software.ulpgc.moneycalculator.architecture.control;

import software.ulpgc.moneycalculator.architecture.model.Currency;
import software.ulpgc.moneycalculator.architecture.model.ExchangeRate;
import software.ulpgc.moneycalculator.architecture.model.Money;
import software.ulpgc.moneycalculator.architecture.view.CurrencyDialog;
import software.ulpgc.moneycalculator.architecture.view.MoneyDialog;
import software.ulpgc.moneycalculator.architecture.view.MoneyDisplay;

import java.util.List;

public class ExchangeCommand implements Command {
    private final MoneyDialog moneyDialog;
    private final CurrencyDialog currencyDialog;
    private final List<ExchangeRate> exchangeRates;
    private final MoneyDisplay moneyDisplay;

    public ExchangeCommand(MoneyDialog moneyDialog, CurrencyDialog currencyDialog, MoneyDisplay moneyDisplay, List<ExchangeRate> exchangeRates) {
        this.moneyDialog = moneyDialog;
        this.currencyDialog = currencyDialog;
        this.moneyDisplay = moneyDisplay;
        this.exchangeRates= exchangeRates;
    }

    @Override
    public void execute() {
        Money money = moneyDialog.get();
        Currency currency = currencyDialog.get();

        double rate = getRate(money.currency(),currency);
        Money result = new Money(money.amount() * rate, currency);

        moneyDisplay.show(result);
    }

    private double getRate(Currency fromCurrency, Currency toCurrency) {
        for (ExchangeRate exchangeRate : exchangeRates) {
            if (exchangeRate.from().equals(fromCurrency) && exchangeRate.to().equals(toCurrency)) {
                return exchangeRate.rate();
            }
        }
        throw new IllegalArgumentException("Tasa de cambio no encontrada para las monedas proporcionadas.");
    }

}
