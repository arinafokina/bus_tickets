package proofit.arina.taxrate.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import proofit.arina.taxrate.exception.TaxRateException;
import proofit.arina.taxrate.model.TaxRateDto;
import proofit.arina.taxrate.model.TaxRateRequest;
import proofit.arina.taxrate.service.TaxRateService;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
class TaxRateControllerTest {

    @InjectMocks
    TaxRateController taxRateController;

    @Mock
    TaxRateService taxRateService;
    @Test
    void getTaxRate_whenNoException_thenReturnsOkResult() throws TaxRateException {
        Mockito.when(taxRateService.getTaxRate(any(Date.class))).thenReturn(21.0);

        var result = taxRateController.getTaxRate(new TaxRateRequest(new Date()));
        assertInstanceOf(ResponseEntity.class, result);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(0, result.getHeaders().size());
        var body = (HashMap<String, Object>)result.getBody();
        assertNotNull(body);
        assertEquals("Tax rate is received.", body.get("message"));
        assertEquals(200, body.get("status"));
        var data = body.get("data");
        assertInstanceOf(TaxRateDto.class, data);
        assertEquals(21.0, ((TaxRateDto)data).getTaxRate());

        Mockito.verify(taxRateService, Mockito.times(1)).getTaxRate(any(Date.class));
    }

    @Test
    void getTaxRate_whenThrowsException_thenReturnsBadRequestResult() throws TaxRateException {
        var errorMessage = "Cannot get tax rate!";
        Mockito.when(taxRateService.getTaxRate(any(Date.class))).thenThrow(new TaxRateException(errorMessage));

        var result = taxRateController.getTaxRate(new TaxRateRequest(new Date()));
        assertInstanceOf(ResponseEntity.class, result);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(0, result.getHeaders().size());
        var body = (HashMap<String, Object>)result.getBody();
        assertNotNull(body);
        assertEquals(errorMessage, body.get("message"));
        assertEquals(400, body.get("status"));
        assertNull(body.get("data"));

        Mockito.verify(taxRateService, Mockito.times(1)).getTaxRate(any(Date.class));
    }
}