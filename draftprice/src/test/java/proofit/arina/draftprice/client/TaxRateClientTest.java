package proofit.arina.draftprice.client;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import proofit.arina.draftprice.exception.DraftPriceException;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class TaxRateClientTest {

    @InjectMocks
    TaxRateClient taxRateClient;

    @Test
    void getTaxRate() throws IOException, DraftPriceException {
        try (MockedStatic<HttpClientBuilder> mocked = mockStatic(HttpClientBuilder.class)) {
            CloseableHttpResponse response = mockHttpResponse(mocked);
            Mockito.when(response.getStatusLine().getStatusCode()).thenReturn(200);
            var json = """
                    {
                        "data": {
                            "taxRate": 21.0
                        },
                        "message": "Tax rate is received.",
                        "status": 200
                    }
                    """;
            HttpEntity entity = Mockito.mock(HttpEntity.class);
            Mockito.when(response.getEntity()).thenReturn(entity);
            try (MockedStatic<EntityUtils> mockedUtils = mockStatic(EntityUtils.class)) {
                mocked.when(() -> EntityUtils.toString(entity)).thenReturn(json);

                var result = taxRateClient.getTaxRate(new Date());
                assertEquals(21.0, result);
            }
        }
    }

    @Test
    void getTaxRate_whenStatusLineCodeIsNot200_thenThrowsException() throws IOException {
        try (MockedStatic<HttpClientBuilder> mocked = mockStatic(HttpClientBuilder.class)) {
            CloseableHttpResponse response = mockHttpResponse(mocked);
            Mockito.when(response.getStatusLine().getStatusCode()).thenReturn(500);
            var json = """
                    {
                        "message": "ERROR!"
                    }
                    """;
            HttpEntity entity = Mockito.mock(HttpEntity.class);
            Mockito.when(response.getEntity()).thenReturn(entity);
            try (MockedStatic<EntityUtils> mockedUtils = mockStatic(EntityUtils.class)) {
                mocked.when(() -> EntityUtils.toString(entity)).thenReturn(json);
                assertThrows(DraftPriceException.class, () -> taxRateClient.getTaxRate(new Date()));
            }
        }
    }

    @Test
    void getTaxRate_whenJsonStatusIsNot200_thenThrowsException() throws IOException {
        try (MockedStatic<HttpClientBuilder> mocked = mockStatic(HttpClientBuilder.class)) {
            CloseableHttpResponse response = mockHttpResponse(mocked);
            Mockito.when(response.getStatusLine().getStatusCode()).thenReturn(200);
            var json = """
                    {
                        "data": {},
                        "message": "Could not get tax rate!",
                        "status": 400
                    }
                    """;
            HttpEntity entity = Mockito.mock(HttpEntity.class);
            Mockito.when(response.getEntity()).thenReturn(entity);
            try (MockedStatic<EntityUtils> mockedUtils = mockStatic(EntityUtils.class)) {
                mocked.when(() -> EntityUtils.toString(entity)).thenReturn(json);
                assertThrows(DraftPriceException.class, () -> taxRateClient.getTaxRate(new Date()));
            }
        }
    }

    @Test
    void getTaxRate_whenIOException_thenThrowsException() throws IOException {
        try (MockedStatic<HttpClientBuilder> mocked = mockStatic(HttpClientBuilder.class)) {
            HttpClientBuilder client = Mockito.mock(HttpClientBuilder.class);
            mocked.when(HttpClientBuilder::create).thenReturn(client);
            CloseableHttpClient httpClient = Mockito.mock(CloseableHttpClient.class);
            Mockito.when(client.build()).thenReturn(httpClient);

            CloseableHttpResponse response = Mockito.mock(CloseableHttpResponse.class, Mockito.RETURNS_DEEP_STUBS);
            Mockito.when(httpClient.execute(any(HttpPost.class))).thenThrow(new IOException("Connection refused."));
            assertThrows(DraftPriceException.class, () -> taxRateClient.getTaxRate(new Date()));
        }
    }

    private CloseableHttpResponse mockHttpResponse(MockedStatic<HttpClientBuilder> mocked) throws IOException {
        HttpClientBuilder client = Mockito.mock(HttpClientBuilder.class);
        mocked.when(HttpClientBuilder::create).thenReturn(client);
        CloseableHttpClient httpClient = Mockito.mock(CloseableHttpClient.class);
        Mockito.when(client.build()).thenReturn(httpClient);

        CloseableHttpResponse response = Mockito.mock(CloseableHttpResponse.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(httpClient.execute(any(HttpPost.class))).thenReturn(response);

        return response;
    }
}