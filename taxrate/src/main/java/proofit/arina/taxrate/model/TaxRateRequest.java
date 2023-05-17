package proofit.arina.taxrate.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaxRateRequest {
    private Date date;

    public TaxRateRequest() {
    }

    public TaxRateRequest(Date date) {
        this.date = date;
    }
}
