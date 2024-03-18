package com.arroganteIT.exchange_rates_bot.service.Impl;

import com.arroganteIT.exchange_rates_bot.client.BankGovUaClient;
import com.arroganteIT.exchange_rates_bot.exception.ServiceException;
import com.arroganteIT.exchange_rates_bot.service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private static final String USD_XPATH = "/exchange/currency[cc='USD']/rate";
    private static final String EUR_XPATH = "/exchange/currency[cc='EUR']/rate";

    private BankGovUaClient client;

    @Autowired
    public ExchangeRatesServiceImpl(BankGovUaClient client) {
        this.client = client;
    }

    @Override
    public String getUSDExchangeRates() throws ServiceException {
        String xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, USD_XPATH);
    }

    @Override
    public String getEURExchangeRates() throws ServiceException {
        String xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml, EUR_XPATH);
    }

//    @Override
//    public String getGBPExchangeRates() throws ServiceException {
//        return null;
//    }
//
//    @Override
//    public String getRUBExchangeRates() throws ServiceException {
//        return null;
//    }
//
//    @Override
//    public String getINRExchangeRates() throws ServiceException {
//        return null;
//    }
//
//    @Override
//    public String getILSExchangeRates() throws ServiceException {
//        return null;
//    }
//
//    @Override
//    public String getBYNExchangeRates() throws ServiceException {
//        return null;
//    }
//
//    @Override
//    public String getTRYExchangeRates() throws ServiceException {
//        return null;
//    }
//
//    @Override
//    public String getPLNExchangeRates() throws ServiceException {
//        return null;
//    }
//
//    @Override
//    public String getAEDExchangeRates() throws ServiceException {
//        return null;
//    }
//
//    @Override
//    public String getXAUExchangeRates() throws ServiceException {
//        return null;
//    }
//
//    @Override
//    public String getXAGExchangeRates() throws ServiceException {
//        return null;
//    }
//
//    @Override
//    public String getXPTExchangeRates() throws ServiceException {
//        return null;
//    }
//
//    @Override
//    public String getXPDExchangeRates() throws ServiceException {
//        return null;
//    }

    private static String extractCurrencyValueFromXML(String xml, String xPathExpression) throws ServiceException {
        InputSource source = new InputSource(new StringReader(xml));
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            Document document = (Document) xPath.evaluate("/", source, XPathConstants.NODE);

            return xPath.evaluate(xPathExpression, document);
        } catch (XPathExpressionException e) {
            throw new ServiceException("Failed to parse XML", e);
        }
    }
}
