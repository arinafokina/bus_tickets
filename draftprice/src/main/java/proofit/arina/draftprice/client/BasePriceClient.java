package proofit.arina.draftprice.client;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import proofit.arina.draftprice.exception.DraftPriceException;

import java.math.BigDecimal;

@Component
public class BasePriceClient extends BaseHttpClient {

    @Value("${app.service.baseprice.address}")
    private String basePriceServiceAddress;

    public BigDecimal getBasePrice(String busTerminalName) throws DraftPriceException {
        JSONObject json = new JSONObject();
        json.put("busTerminalName", busTerminalName);

        String payload = json.toString();
        String uri = basePriceServiceAddress + "/basePrice";
        var response = post(uri, payload);
        return response.getBigDecimal("basePrice");
    }
}
