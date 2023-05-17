package proofit.arina.draftprice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proofit.arina.draftprice.client.BasePriceClient;
import proofit.arina.draftprice.exception.DraftPriceException;

import java.math.BigDecimal;

@Service
public class BasePriceService {

    @Autowired
    private BasePriceClient basePriceClient;

    public BasePriceService(BasePriceClient basePriceClient) {
        this.basePriceClient = basePriceClient;
    }

    public BigDecimal getTicketBasePrice(String busTerminalName) throws DraftPriceException {
        return basePriceClient.getBasePrice(busTerminalName);
    }
}
