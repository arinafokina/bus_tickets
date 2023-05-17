package proofit.arina.draftprice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import proofit.arina.draftprice.exception.DraftPriceException;
import proofit.arina.draftprice.model.DraftPriceRequest;
import proofit.arina.draftprice.model.Passenger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class DraftPriceServiceTest {
    @InjectMocks
    DraftPriceService draftPriceService;
    @Mock
    TaxRateService taxRateService;
    @Mock
    BasePriceService basePriceService;

    @Test
    void calculateDraftPrice() throws DraftPriceException {
        Mockito.when(basePriceService.getTicketBasePrice(anyString())).thenReturn(BigDecimal.valueOf(10));
        Mockito.when(taxRateService.getTaxRate(any(Date.class))).thenReturn(21.0);
        var passengers = new ArrayList<Passenger>(){{
            add(new Passenger(false, 2));
            add(new Passenger(true, 1));
        }};

        var date = new Date();
        var busTerminalName = "Vilnius";
        var request = new DraftPriceRequest(passengers, date, busTerminalName);
        var result = draftPriceService.calculateDraftPrice(request);

        assertEquals(new BigDecimal("12.10"), result.getAdultsTotalPrice());
        assertEquals(BigDecimal.valueOf(7.26), result.getAdultsBagsTotalPrice());
        assertEquals(BigDecimal.valueOf(6.05), result.getChildrenTotalPrice());
        assertEquals(BigDecimal.valueOf(3.63), result.getChildrenBagsTotalPrice());
        assertEquals(BigDecimal.valueOf(29.04), result.getTotalPrice());

        Mockito.verify(basePriceService, Mockito.times(1)).getTicketBasePrice(busTerminalName);
        Mockito.verify(taxRateService, Mockito.times(1)).getTaxRate(date);
    }
}