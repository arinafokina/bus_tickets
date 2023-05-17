package proofit.arina.taxrate.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import proofit.arina.taxrate.exception.TaxRateException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaxRateServiceTest {

    @InjectMocks
    TaxRateService taxRateService;

    @Test
    void getTaxRate_whenDateIsProvided_thenReturnTaxRate() throws TaxRateException {
        var result = taxRateService.getTaxRate(new Date());
        assertEquals(21.0, result);
    }

    @Test
    void getTaxRate_whenDateIsNotProvided_thenThrowsException() {
        assertThrows(TaxRateException.class, () -> taxRateService.getTaxRate(null));
    }
}