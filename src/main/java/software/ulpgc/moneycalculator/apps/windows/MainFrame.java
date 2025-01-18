package software.ulpgc.moneycalculator.apps.windows;

import software.ulpgc.moneycalculator.apps.windows.view.SwingCurrencyDialog;
import software.ulpgc.moneycalculator.apps.windows.view.SwingMoneyDialog;
import software.ulpgc.moneycalculator.apps.windows.view.SwingMoneyDisplay;
import software.ulpgc.moneycalculator.architecture.control.Command;
import software.ulpgc.moneycalculator.architecture.control.ExchangeCommand;
import software.ulpgc.moneycalculator.architecture.model.Currency;
import software.ulpgc.moneycalculator.architecture.view.CurrencyDialog;
import software.ulpgc.moneycalculator.architecture.view.MoneyDialog;
import software.ulpgc.moneycalculator.architecture.view.MoneyDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
    private final List<Currency> currencies;
    private final SwingMoneyDialog moneyDialog;
    private final SwingCurrencyDialog currencyDialog;
    private final SwingMoneyDisplay moneyDisplay;
    private final JLabel exchangeRateLabel;
    private final Map<String, Command> commands;

    public MainFrame(List<Currency> currencies) {
        this.currencies = currencies;
        this.setTitle("MoneyCalculator");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.add(moneyDialog = createMoneyDialog());
        this.add(currencyDialog = createCurrencyDialog());
        this.add(moneyDisplay = createMoneyDisplay());
        this.add(createCalculateButton());
        this.add(exchangeRateLabel = createExchangeRateLabel());
        this.commands = new HashMap<>();
    }

    private SwingMoneyDialog createMoneyDialog() {
        return new SwingMoneyDialog(currencies);
    }

    private SwingCurrencyDialog createCurrencyDialog() {
        return new SwingCurrencyDialog(currencies);
    }

    private SwingMoneyDisplay createMoneyDisplay() {
        return new SwingMoneyDisplay();
    }

    private JLabel createExchangeRateLabel() {
        JLabel label = new JLabel("Exchange Rate: ");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }

    private Component createCalculateButton() {
        JButton button = new JButton("Calculate");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command command = commands.get("exchange");
                if (command != null) {
                    command.execute();

                    if (command instanceof ExchangeCommand) {
                        ExchangeCommand exchangeCommand = (ExchangeCommand) command;
                        double rate = exchangeCommand.getChoosenRate();
                        boolean isFavorable = exchangeCommand.isFavorableExchange();

                        exchangeRateLabel.setText(String.format("Exchange Rate: %.4f", rate));
                        exchangeRateLabel.setForeground(isFavorable ? Color.GREEN : Color.RED);
                    }
                }
            }
        });
        return button;
    }

    public MoneyDialog moneyDialog() {
        return moneyDialog;
    }

    public CurrencyDialog currencyDialog() {
        return currencyDialog;
    }

    public MoneyDisplay moneyDisplay() {
        return moneyDisplay;
    }

    public void add(String operation, Command command) {
        commands.put(operation, command);
    }
}
