package proofit.arina.baseprice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import proofit.arina.baseprice.exception.BasePriceException;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BasePriceServiceTest {

    @InjectMocks
    BasePriceService basePriceService;

    @Test
    void getBasePrice_whenBusTerminalNameIsProvided_thenReturnTaxRate() throws BasePriceException {
        var result = basePriceService.getBasePrice("Vilnius");
        assertEquals(BigDecimal.valueOf(10), result);
    }

    @Test
    void getBasePrice_whenBusTerminalNameIsNotProvided_thenThrowsException() {
        assertThrows(BasePriceException.class, () -> basePriceService.getBasePrice(null));
    }
}