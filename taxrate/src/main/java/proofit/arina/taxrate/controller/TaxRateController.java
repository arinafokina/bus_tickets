package proofit.arina.taxrate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import proofit.arina.taxrate.exception.TaxRateException;
import proofit.arina.taxrate.model.TaxRateRequest;
import proofit.arina.taxrate.model.TaxRateDto;
import proofit.arina.taxrate.response.ResponseHandler;
import proofit.arina.taxrate.service.TaxRateService;

@RestController
public class TaxRateController {

    private static final Logger logger = LoggerFactory.getLogger(TaxRateController.class);

    @Autowired
    private TaxRateService taxRateService;

    @PostMapping("/taxRate")
    public ResponseEntity<Object> getTaxRate(@RequestBody(required = false) TaxRateRequest request) {
        try {
            return ResponseHandler.ok("Tax rate is received.", new TaxRateDto(taxRateService.getTaxRate(request.getDate())));
        } catch (TaxRateException e) {
            logger.error(e.getMessage(), e);
            return ResponseHandler.badRequest(e.getMessage(), null);
        }
    }
}
