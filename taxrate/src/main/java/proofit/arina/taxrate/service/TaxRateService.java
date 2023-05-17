package proofit.arina.taxrate.service;

import org.springframework.stereotype.Service;
import proofit.arina.taxrate.exception.TaxRateException;

import java.util.Date;

@Service
public class TaxRateService {

    public double getTaxRate(Date date) throws TaxRateException {
        if (date != null) {
            return 21.0;
        }

        throw new TaxRateException("Cannot calculate tax rate: Date is not provided.");
    }
}
