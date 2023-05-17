package proofit.arina.taxrate.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxRateDto {
    private double taxRate;

    public TaxRateDto(double taxRate) {
        this.taxRate = taxRate;
    }
}
