package com.leonid.LotteryBot.dispather.handler;

import com.leonid.LotteryBot.db.UserRepository;
import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.service.MessageService;
import com.leonid.LotteryBot.service.UserSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
public class CancelMessageHandler extends AbstractMessageHandler {

    private final UserSessionManager userSessionManager;

    @Autowired
    protected CancelMessageHandler(MessageService messageService, UserRepository userRepository, UserSessionManager userSessionManager) {
        super(messageService, userRepository);
        this.userSessionManager = userSessionManager;
    }

    @Override
    public SendMessage handle(Message message) {
        User user = getUserFromMessage(message);
        userSessionManager.resetUserState(user);
        return messageService.createTextMessage(user, "Операция отменена");
    }
}
