package proofit.arina.draftprice.client;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import proofit.arina.draftprice.exception.DraftPriceException;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TaxRateClient extends BaseHttpClient{

    @Value("${app.service.taxrate.address}")
    private String taxRateServiceAddress;

    public double getTaxRate(Date date) throws DraftPriceException {
        JSONObject json = new JSONObject();
        json.put("date", new SimpleDateFormat("yyyy-MM-dd").format(date));

        String payload = json.toString();
        String uri = taxRateServiceAddress + "/taxRate";
        var response = post(uri, payload);
        return response.getDouble("taxRate");
    }
}
