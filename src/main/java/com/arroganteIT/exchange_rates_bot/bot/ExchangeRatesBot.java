package com.arroganteIT.exchange_rates_bot.bot;

import com.arroganteIT.exchange_rates_bot.exception.ServiceException;
import com.arroganteIT.exchange_rates_bot.service.ExchangeRatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;

@Component
public class ExchangeRatesBot extends TelegramLongPollingBot {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRatesBot.class);

    private static final String START = "/start";
    private static final String USD = "/usd";
    private static final String EUR = "/eur";
    private static final String RUB = "/rub";
    private static final String HELP = "/help";

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    public ExchangeRatesBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        switch (message) {
            case START:
                String userName = update.getMessage().getChat().getUserName();
                startCommand(chatId, userName);
                break;
            case USD:
                usdCommand(chatId);
                break;
            case EUR:
                eurCommand(chatId);
                break;
            case RUB:
                rubCommand(chatId);
                break;
            case HELP:
                helpCommand(chatId);
                break;
            default:
                unknownCommand(chatId);
        }
    }

    @Override
    public String getBotUsername() {
        return "arroganteIT_er_bot";
    }

    private void startCommand(Long chatId, String userName) {
        String text = "\n" +
                "Добро пожаловать в бот, %s!\n\n" +

                "Здесь Вы сможете узнать официальные курсы валют на сегодня,\n" +
                "установленные Национальным банком Украины.\n\n" +

                "Для этого воспользуйтесь командами:\n" +
                "/usd - курс доллара\n" +
                "/eur - курс евро\n" +
                "/rub - курс рубля\n\n" +

                "Дополнительные команды:\n" +
                "/help - получение справки";

        String format = String.format(text, userName);
        sendMessage(chatId, format);
    }

    private void usdCommand(Long chatId) {
        String format;

        try {
            String usd = exchangeRatesService.getUSDExchangeRates();
            String text = "Курс доллара на %s составляет %s гривен";
            format = String.format(text, LocalDate.now(), usd);
        } catch (ServiceException e) {
            LOG.error("Ошибка получения курса доллара", e);
            format = "Не удалось получить текущий курс доллара. Попробуйте позже.";
        }

        sendMessage(chatId, format);
    }

    private void eurCommand(Long chatId) {
        String format;

        try {
            String eur = exchangeRatesService.getEURExchangeRates();
            String text = "Курс евро на %s составляет %s гривен";
            format = String.format(text, LocalDate.now(), eur);
        } catch (ServiceException e) {
            LOG.error("Ошибка получения курса евро", e);
            format = "Не удалось получить текущий курс евро. Попробуйте позже.";
        }

        sendMessage(chatId, format);
    }

    private void rubCommand(Long chatId) {
        String format;

        try {
            String rub = exchangeRatesService.getRUBExchangeRates();
            String text = "Курс рубля на %s составляет %s гривен";
            format = String.format(text, LocalDate.now(), rub);
        } catch (ServiceException e) {
            LOG.error("Ошибка получения курса рубля", e);
            format = "Не удалось получить текущий курс рубля. Попробуйте позже.";
        }

        sendMessage(chatId, format);
    }

    private void helpCommand(Long chatId) {
        String text = "\n" +
                "Справочная информация по боту\n\n" +
                "Для получения текущих курсов валют воспользуйтесь командами:\n" +
                "/usd - курс доллара\n" +
                "/eur - курс евро\n" +
                "/rub - курс рубля";

        sendMessage(chatId, text);
    }

    private void unknownCommand(Long chatId) {
        String text = "Не удалось распознать команду!";
        sendMessage(chatId, text);
    }

    private void sendMessage(Long chatId, String text) {
        String chatIdString = String.valueOf(chatId);
        SendMessage sendMessage = new SendMessage(chatIdString, text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            LOG.error("Ошибка отправки сообщения", e);
        }
    }
}
