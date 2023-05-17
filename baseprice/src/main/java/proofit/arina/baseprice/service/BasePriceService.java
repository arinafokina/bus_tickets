package proofit.arina.baseprice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import proofit.arina.baseprice.exception.BasePriceException;

import java.math.BigDecimal;

@Service
public class BasePriceService {

    public BigDecimal getBasePrice(String busTerminalName) throws BasePriceException {
        if ("Vilnius".equals(busTerminalName)) {
            return BigDecimal.valueOf(10);
        }

        throw new BasePriceException(String.format("Cannot get base price: Given bus terminal name (%s) is not recognized.", busTerminalName));
    }
}
