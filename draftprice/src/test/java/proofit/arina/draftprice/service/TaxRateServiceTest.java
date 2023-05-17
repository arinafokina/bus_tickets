package proofit.arina.draftprice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import proofit.arina.draftprice.client.TaxRateClient;
import proofit.arina.draftprice.exception.DraftPriceException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TaxRateServiceTest {

    @InjectMocks
    TaxRateService taxRateService;
    @Mock
    private TaxRateClient taxRateClient;

    @Test
    void getTaxRate() throws DraftPriceException {
        assertNotNull(taxRateClient);
        Mockito.when(taxRateClient.getTaxRate(any(Date.class))).thenReturn(21.0);

        var date = new Date();
        var result = taxRateService.getTaxRate(date);
        assertEquals(21.0, result);
        Mockito.verify(taxRateClient, Mockito.times(1)).getTaxRate(any(Date.class));
        Mockito.verify(taxRateClient, Mockito.times(1)).getTaxRate(date);
    }
}