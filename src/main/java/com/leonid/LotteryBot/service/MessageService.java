package com.leonid.LotteryBot.service;

import com.leonid.LotteryBot.domain.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class MessageService {

    public SendMessage createGreetingMessage(User user) {
        return new SendMessage()
                .setChatId(user.getUid())
                .setText("Hello, " + user.getUsername());
    }


    public SendMessage createUserBalanceMessage(User user) {
        return new SendMessage()
                .setChatId(user.getUid())
                .setText("Your balance: " + user.getUserBalance().getMoney());
    }


    public SendMessage createAskForBetMessage(User user) {
        return createTextMessage(user, "Какова ставка комнаты?");
    }

    public SendMessage createTextMessage(User user, String message) {
        return new SendMessage()
                .setChatId(user.getUid())
                .setText(message);
    }

    public SendMessage createRoomCreatedMessage(User user) {
        return createTextMessage(user, "Комната создана");
    }
}
