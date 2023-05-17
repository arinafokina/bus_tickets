package proofit.arina.draftprice.client;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proofit.arina.draftprice.exception.DraftPriceException;

import java.io.IOException;

public abstract class BaseHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(BaseHttpClient.class);
    public JSONObject post(String uri, String payload) throws DraftPriceException {
        StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(uri);
            request.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                validateResponse(response);
                var json = new JSONObject(EntityUtils.toString(response.getEntity()));
                validateJsonResponse(json);
                return json.getJSONObject("data");
            }
        } catch (IOException e) {
            logger.error(String.format("Error sending a post request to URI %s : %s", uri, e.getMessage()));
            throw new DraftPriceException(String.format("Error sending a post request to URI %s : %s", uri, e.getMessage()));
        }
    }

    private void validateResponse(CloseableHttpResponse response) throws DraftPriceException, IOException {
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            var jsonResponse = new JSONObject(EntityUtils.toString(response.getEntity()));
            throw new DraftPriceException(jsonResponse.getString("message"));
        }
    }

    private void validateJsonResponse(JSONObject response) throws DraftPriceException, IOException {
        if (response.getInt("status") != org.springframework.http.HttpStatus.OK.value()) {
            throw new DraftPriceException(response.getString("message"));
        }
    }
}