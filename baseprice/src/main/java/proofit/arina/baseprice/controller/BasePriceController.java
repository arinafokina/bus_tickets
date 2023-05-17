package proofit.arina.baseprice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import proofit.arina.baseprice.exception.BasePriceException;
import proofit.arina.baseprice.model.BasePriceRequest;
import proofit.arina.baseprice.model.BasePriceDto;
import proofit.arina.baseprice.response.ResponseHandler;
import proofit.arina.baseprice.service.BasePriceService;

@RestController
public class BasePriceController {

    private static final Logger logger = LoggerFactory.getLogger(BasePriceController.class);
    @Autowired
    private BasePriceService basePriceService;

    @PostMapping("/basePrice")
    public ResponseEntity<Object> getBasePrice(@RequestBody BasePriceRequest request) {
        try {
            return ResponseHandler.ok("Base price is received.", new BasePriceDto(basePriceService.getBasePrice(request.getBusTerminalName())));
        } catch (BasePriceException e) {
            logger.error(e.getMessage(), e);
            return ResponseHandler.badRequest(e.getMessage(), null);
        }
    }
}
