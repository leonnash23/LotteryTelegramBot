package com.leonid.LotteryBot.service;

import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.domain.UserBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class MessageService {

    private final UserService userService;

    @Autowired
    public MessageService(UserService userService) {
        this.userService = userService;
    }

    public SendMessage createGreetingMessage(User user) {
        return new SendMessage()
                .setChatId(user.getUid())
                .setText("Hello, " + user.getUsername());
    }


    public SendMessage createUserBalanceMessage(User user) {
        UserBalance userBalance = userService.getUserBalance(user);
        return new SendMessage()
                .setChatId(user.getUid())
                .setText("Your balance: " + userBalance.getMoney());
    }


    public SendMessage createTextMessage(User user, String message) {
        return new SendMessage()
                .setChatId(user.getUid())
                .setText(message);
    }
}
