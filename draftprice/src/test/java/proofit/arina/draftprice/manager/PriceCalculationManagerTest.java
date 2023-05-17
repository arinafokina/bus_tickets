package proofit.arina.draftprice.manager;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import proofit.arina.draftprice.enums.DiscountType;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceCalculationManagerTest {

    @ParameterizedTest
    @MethodSource("provideValuesForCalculatePriceWithTax")
    void calculatePriceWithTax(int count, DiscountType discountType, String expectedPrice) {
        var basePrice = BigDecimal.valueOf(10);
        var taxRate = 21;

        var calculator = new PriceCalculationManager(basePrice, taxRate, count, discountType);
        var price = calculator.calculatePriceWithTax();

        assertEquals(new BigDecimal(expectedPrice), price);
    }

    private static Stream<Arguments> provideValuesForCalculatePriceWithTax() {
        return Stream.of(
                Arguments.of(1, DiscountType.NONE, "12.10"),
                Arguments.of(2, DiscountType.LUGGAGE, "7.26"),
                Arguments.of(1, DiscountType.CHILDREN, "6.05"),
                Arguments.of(1, DiscountType.LUGGAGE, "3.63"),
                Arguments.of(0, DiscountType.NONE, "0"),
                Arguments.of(0, DiscountType.LUGGAGE, "0"),
                Arguments.of(0, DiscountType.CHILDREN, "0")
        );
    }
}