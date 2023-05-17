package proofit.arina.draftprice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proofit.arina.draftprice.client.TaxRateClient;
import proofit.arina.draftprice.exception.DraftPriceException;

import java.util.Date;

@Service
public class TaxRateService {

    @Autowired
    private TaxRateClient taxRateClient;

    public TaxRateService(TaxRateClient taxRateClient) {
        this.taxRateClient = taxRateClient;
    }

    public double getTaxRate(Date date) throws DraftPriceException {
        return taxRateClient.getTaxRate(date);
    }
}
