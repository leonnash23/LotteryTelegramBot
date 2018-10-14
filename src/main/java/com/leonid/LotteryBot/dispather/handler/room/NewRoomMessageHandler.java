package com.leonid.LotteryBot.dispather.handler.room;

import com.leonid.LotteryBot.db.UserRepository;
import com.leonid.LotteryBot.dispather.handler.AbstractMessageHandler;
import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.service.MessageService;
import com.leonid.LotteryBot.service.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class NewRoomMessageHandler extends AbstractMessageHandler {

    private final UserSessionManager userSessionManager;

    protected NewRoomMessageHandler(MessageService messageService, UserRepository userRepository, UserSessionManager userSessionManager) {
        super(messageService, userRepository);
        this.userSessionManager = userSessionManager;
    }

    @Override
    public SendMessage handle(Message message) {
        User user = getUserFromMessage(message);
        userSessionManager.addUserWaitForRoomBet(user);
        return messageService.createTextMessage(user, "Какова ставка комнаты?");
    }
}
