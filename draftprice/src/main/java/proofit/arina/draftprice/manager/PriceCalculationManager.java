package proofit.arina.draftprice.manager;

import lombok.Getter;
import lombok.Setter;
import proofit.arina.draftprice.enums.DiscountType;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class PriceCalculationManager {
    private BigDecimal basePrice;
    private double taxRate;
    private int count;
    private DiscountType discountType;

    public PriceCalculationManager(BigDecimal basePrice, double taxRate, int count, DiscountType discountType) {
        this.basePrice = basePrice;
        this.taxRate = taxRate;
        this.count = count;
        this.discountType = discountType;
    }

    public BigDecimal calculatePriceWithTax() {
        if (count == 0) {
            return BigDecimal.valueOf(0);
        }
        var price = basePrice
                .multiply(BigDecimal.valueOf(count))
                .multiply(BigDecimal.valueOf(discountType.getDiscount()));
        var priceWithTax = addTaxToPrice(price, taxRate);
        return roundPrice(priceWithTax);
    }

    private BigDecimal addTaxToPrice(BigDecimal price, double taxRate) {
        var tax = price.multiply(BigDecimal.valueOf(taxRate / 100));
        return price.add(tax);
    }

    private static BigDecimal roundPrice(BigDecimal price) {
        return price.setScale(2, RoundingMode.HALF_EVEN);
    }
}
