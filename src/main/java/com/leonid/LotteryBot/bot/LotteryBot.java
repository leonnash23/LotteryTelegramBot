package com.leonid.LotteryBot.bot;

import com.leonid.LotteryBot.dispather.MessageDispatcher;
import com.leonid.LotteryBot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class LotteryBot extends TelegramLongPollingBot {

    @Value("${token}")
    private String token;
    private final UserService userService;
    private final MessageDispatcher messageDispatcher;

    @Autowired
    public LotteryBot(UserService userService, MessageDispatcher messageDispatcher) {
        this.userService = userService;
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    public void onUpdateReceived(Update update) {
        createNewUserIfNotExist(update);
        if (update.hasMessage()) {
            dispatchMessage(update);
        }

    }

    private void dispatchMessage(Update update) {
        BotApiMethod answer = messageDispatcher.dispatch(update.getMessage());
        if (answer != null) {
            try {
                //noinspection unchecked
                execute(answer);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void createNewUserIfNotExist(Update update) {
        Long uid = update.getMessage().getChatId();
        if (!userService.isUserExist(uid)) {
            userService.createAndSaveUser(uid, update.getMessage().getFrom().getUserName());
        }
    }

    @Override
    public String getBotUsername() {
        return "test";
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
