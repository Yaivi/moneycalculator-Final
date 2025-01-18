package software.ulpgc.moneycalculator.architecture.persistence;

import software.ulpgc.moneycalculator.architecture.model.Currency;

import java.util.List;

public interface CurrencyLoader {
    List<Currency> load();
}
