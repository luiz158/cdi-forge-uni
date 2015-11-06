package org.expenses.core.service;

import org.expenses.core.model.Currency;
import org.expenses.core.utils.Loggable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Loggable
public class CurrencyService {

    @Inject
    private RateService rateService;

    public Float change(Float amount, Currency currency) {
        return (float) (amount * changeRate(currency));
    }

    public double changeRate(Currency currency) {
        if (currency == Currency.EURO)
            return rateService.rate();
        else
            return 1 / rateService.rate();

    }
}
