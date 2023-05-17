package proofit.arina.baseprice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import proofit.arina.baseprice.exception.BasePriceException;
import proofit.arina.baseprice.model.BasePriceDto;
import proofit.arina.baseprice.model.BasePriceRequest;
import proofit.arina.baseprice.service.BasePriceService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class BasePriceControllerTest {

    @InjectMocks
    BasePriceController basePriceController;

    @Mock
    BasePriceService basePriceService;
    @Test
    void getBasePrice_whenNoException_thenReturnsOkResult() throws BasePriceException {
        Mockito.when(basePriceService.getBasePrice(anyString())).thenReturn(BigDecimal.valueOf(10));

        var result = basePriceController.getBasePrice(new BasePriceRequest("Vilnius"));
        assertInstanceOf(ResponseEntity.class, result);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(0, result.getHeaders().size());
        var body = (HashMap<String, Object>)result.getBody();
        assertNotNull(body);
        assertEquals("Base price is received.", body.get("message"));
        assertEquals(200, body.get("status"));
        var data = body.get("data");
        assertInstanceOf(BasePriceDto.class, data);
        assertEquals(BigDecimal.valueOf(10), ((BasePriceDto)data).getBasePrice());

        Mockito.verify(basePriceService, Mockito.times(1)).getBasePrice(anyString());
    }

    @Test
    void getBasePrice_whenThrowsException_thenReturnsBadRequestResult() throws BasePriceException {
        var errorMessage = "Cannot get base price!";
        Mockito.when(basePriceService.getBasePrice(anyString())).thenThrow(new BasePriceException(errorMessage));

        var result = basePriceController.getBasePrice(new BasePriceRequest("Vilnius"));
        assertInstanceOf(ResponseEntity.class, result);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(0, result.getHeaders().size());
        var body = (HashMap<String, Object>)result.getBody();
        assertNotNull(body);
        assertEquals(errorMessage, body.get("message"));
        assertEquals(400, body.get("status"));
        assertNull(body.get("data"));

        Mockito.verify(basePriceService, Mockito.times(1)).getBasePrice(anyString());
    }
}