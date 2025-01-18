package software.ulpgc.moneycalculator.architecture.persistence;

import software.ulpgc.moneycalculator.architecture.model.ExchangeRate;

import java.util.List;

public interface ExchangeRateLoader {
    List<ExchangeRate> load();
}
