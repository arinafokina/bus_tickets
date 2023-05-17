package proofit.arina.baseprice.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BasePriceDto {
    private BigDecimal basePrice;

    public BasePriceDto(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
}
