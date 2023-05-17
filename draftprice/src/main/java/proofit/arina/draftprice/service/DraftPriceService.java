package proofit.arina.draftprice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proofit.arina.draftprice.enums.DiscountType;
import proofit.arina.draftprice.exception.DraftPriceException;
import proofit.arina.draftprice.manager.PriceCalculationManager;
import proofit.arina.draftprice.model.DraftPriceRequest;
import proofit.arina.draftprice.model.DraftPriceDto;
import proofit.arina.draftprice.model.Passenger;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DraftPriceService {

    private static final Logger logger = LoggerFactory.getLogger(DraftPriceService.class);
    @Autowired
    private BasePriceService basePriceService;
    @Autowired
    private TaxRateService taxRateService;

    public DraftPriceService(BasePriceService basePriceService, TaxRateService taxRateService) {
        this.basePriceService = basePriceService;
        this.taxRateService = taxRateService;
    }

    public DraftPriceDto calculateDraftPrice(DraftPriceRequest request) throws DraftPriceException {
        var basePrice = basePriceService.getTicketBasePrice(request.getBusTerminalName());
        logger.info(String.format("Base price for bus terminal '%s': %.2f EUR", request.getBusTerminalName(), basePrice));
        var taxRate = taxRateService.getTaxRate(request.getDate());
        logger.info(String.format("Tax rate for '%tF': %.0f%%", request.getDate(), taxRate));

        var response = new DraftPriceDto();

        calculateAndSetAdultsData(request.getPassengers(), basePrice, taxRate, response);
        calculateAndSetChildrenData(request.getPassengers(), basePrice, taxRate, response);

        return response;
    }

    private void calculateAndSetAdultsData(List<Passenger> passengers, BigDecimal basePrice, double taxRate, DraftPriceDto response){
        var adults = passengers.stream().filter(p -> !p.isChild()).toList();
        var adultsBagsCount = getTotalBagsCount(adults);

        var adultsPrice = new PriceCalculationManager(basePrice, taxRate, adults.size(), DiscountType.NONE).calculatePriceWithTax();
        var adultsBagsPrice = new PriceCalculationManager(basePrice, taxRate, adultsBagsCount, DiscountType.LUGGAGE).calculatePriceWithTax();

        response.setAdultsTotalPrice(adultsPrice);
        response.setAdultsBagsTotalPrice(adultsBagsPrice);
    }

    private void calculateAndSetChildrenData(List<Passenger> passengers, BigDecimal basePrice, double taxRate, DraftPriceDto response){
        var children = passengers.stream().filter(Passenger::isChild).toList();
        var childrenBagsCount = getTotalBagsCount(children);

        var childrenPrice = new PriceCalculationManager(basePrice, taxRate, children.size(), DiscountType.CHILDREN).calculatePriceWithTax();
        var childrenBagsPrice = new PriceCalculationManager(basePrice, taxRate, childrenBagsCount, DiscountType.LUGGAGE).calculatePriceWithTax();

        response.setChildrenTotalPrice(childrenPrice);
        response.setChildrenBagsTotalPrice(childrenBagsPrice);
    }

    private int getTotalBagsCount(List<Passenger> passengers){
        return passengers.stream().mapToInt(Passenger::getBagsCount).sum();
    }
}