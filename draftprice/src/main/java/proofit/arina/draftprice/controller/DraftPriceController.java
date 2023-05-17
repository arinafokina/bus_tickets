package proofit.arina.draftprice.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import proofit.arina.draftprice.exception.DraftPriceException;
import proofit.arina.draftprice.model.DraftPriceRequest;
import proofit.arina.draftprice.response.ResponseHandler;
import proofit.arina.draftprice.service.DraftPriceService;

@RestController
public class DraftPriceController {

    private static final Logger logger = LoggerFactory.getLogger(DraftPriceController.class);

    @Autowired
    private DraftPriceService draftPriceService;

    public DraftPriceController(DraftPriceService draftPriceService) {
        this.draftPriceService = draftPriceService;
    }

    @PostMapping("/draftPrice")
    public ResponseEntity<Object> calculateDraftPrice(@Valid @RequestBody DraftPriceRequest request) {
        try {
            return ResponseHandler.ok("Draft price is successfully calculated.",
                    draftPriceService.calculateDraftPrice(request));
        } catch (DraftPriceException e) {
            logger.error(e.getMessage(), e);
            return ResponseHandler.badRequest(e.getMessage(), null);
        }
    }
}
