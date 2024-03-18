package com.arroganteIT.exchange_rates_bot.client;

import com.arroganteIT.exchange_rates_bot.exception.ServiceException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BankGovUaClient {

    @Value("${bankgovua.currency.rates.xml.url}")
    private String url;

    private OkHttpClient okHttpClient;

    @Autowired
    public BankGovUaClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public String getCurrencyRatesXML() throws ServiceException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            return body == null ? null : body.string();
        } catch (IOException e) {
            throw new ServiceException("Error getting exchange rates", e);
        }
    }
}
