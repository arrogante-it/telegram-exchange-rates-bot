package com.arroganteIT.exchange_rates_bot.service;

import com.arroganteIT.exchange_rates_bot.exception.ServiceException;

public interface ExchangeRatesService {

    String getUSDExchangeRates() throws ServiceException; // USA, Доллар

    String getEURExchangeRates() throws ServiceException; // EURO, Евро

    String getRUBExchangeRates() throws ServiceException; // Russia, Рубль

    //    String getGBPExchangeRates() throws ServiceException; // GBP, Фунт стерлингов

//    String getINRExchangeRates() throws ServiceException; // India, Индийская рупия
//
//    String getILSExchangeRates() throws ServiceException; // Israel, Израильский шекель
//
//    String getBYNExchangeRates() throws ServiceException; // Belarus, Беларусский рубль
//
//    String getTRYExchangeRates() throws ServiceException; // Türkiye, Турецкая лира
//
//    String getPLNExchangeRates() throws ServiceException; // Poland, Злотый
//
//    String getAEDExchangeRates() throws ServiceException; // OAE, Дирхам
//
//    String getXAUExchangeRates() throws ServiceException; // Gold
//
//    String getXAGExchangeRates() throws ServiceException; // Silver
//
//    String getXPTExchangeRates() throws ServiceException; // Platinum
//
//    String getXPDExchangeRates() throws ServiceException; // Palladium

}
