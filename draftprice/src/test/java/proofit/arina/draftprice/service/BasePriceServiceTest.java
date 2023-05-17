package proofit.arina.draftprice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import proofit.arina.draftprice.client.BasePriceClient;
import proofit.arina.draftprice.exception.DraftPriceException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class BasePriceServiceTest {
    @InjectMocks
    BasePriceService basePriceService;

    @Mock
    private BasePriceClient basePriceClient;

    @Test
    public void getTicketBasePrice() throws DraftPriceException {
        assertNotNull(basePriceClient);
        Mockito.when(basePriceClient.getBasePrice(anyString())).thenReturn(BigDecimal.valueOf(10));

        var busTerminalName = "ABC";
        var result = basePriceService.getTicketBasePrice(busTerminalName);
        assertEquals(BigDecimal.valueOf(10), result);
        Mockito.verify(basePriceClient, Mockito.times(1)).getBasePrice(anyString());
        Mockito.verify(basePriceClient, Mockito.times(1)).getBasePrice(busTerminalName);
    }

}