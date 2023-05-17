package proofit.arina.draftprice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import proofit.arina.draftprice.exception.DraftPriceException;
import proofit.arina.draftprice.model.DraftPriceDto;
import proofit.arina.draftprice.model.DraftPriceRequest;
import proofit.arina.draftprice.service.DraftPriceService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
@ExtendWith(MockitoExtension.class)
class DraftPriceControllerTest {

    @InjectMocks
    DraftPriceController draftPriceController;
    @Mock
    DraftPriceService draftPriceService;

    @Test
    void calculateDraftPrice_whenNoException_thenReturnsOkResult() throws DraftPriceException {
        var priceResponse = new DraftPriceDto();
        Mockito.when(draftPriceService.calculateDraftPrice(any(DraftPriceRequest.class))).thenReturn(priceResponse);

        var result = draftPriceController.calculateDraftPrice(new DraftPriceRequest(new ArrayList<>(), new Date(), anyString()));
        assertInstanceOf(ResponseEntity.class, result);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(0, result.getHeaders().size());
        var body = (HashMap<String, Object>)result.getBody();
        assertNotNull(body);
        assertEquals("Draft price is successfully calculated.", body.get("message"));
        assertEquals(200, body.get("status"));
        assertEquals(priceResponse, body.get("data"));

        Mockito.verify(draftPriceService, Mockito.times(1)).calculateDraftPrice(any(DraftPriceRequest.class));
    }

    @Test
    void calculateDraftPrice_whenThrowsException_thenReturnsBadRequestResult() throws DraftPriceException {
        var errorMessage = "Cannot get prices!";
        Mockito.when(draftPriceService.calculateDraftPrice(any(DraftPriceRequest.class))).thenThrow(new DraftPriceException(errorMessage));

        var result = draftPriceController.calculateDraftPrice(new DraftPriceRequest(new ArrayList<>(), new Date(), anyString()));
        assertInstanceOf(ResponseEntity.class, result);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(0, result.getHeaders().size());
        var body = (HashMap<String, Object>)result.getBody();
        assertNotNull(body);
        assertEquals("Cannot get prices!", body.get("message"));
        assertEquals(400, body.get("status"));
        assertNull(body.get("data"));

        Mockito.verify(draftPriceService, Mockito.times(1)).calculateDraftPrice(any(DraftPriceRequest.class));
    }
}