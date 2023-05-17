package proofit.arina.draftprice.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DraftPriceDto {

    private BigDecimal adultsTotalPrice;
    private BigDecimal adultsBagsTotalPrice;
    private BigDecimal childrenTotalPrice;
    private BigDecimal childrenBagsTotalPrice;
    public BigDecimal getTotalPrice(){
        return adultsTotalPrice.add(adultsBagsTotalPrice).add(childrenTotalPrice).add(childrenBagsTotalPrice);
    }
}
