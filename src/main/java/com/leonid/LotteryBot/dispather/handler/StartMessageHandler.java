package com.leonid.LotteryBot.dispather.handler;

import com.leonid.LotteryBot.db.UserRepository;
import com.leonid.LotteryBot.service.MessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class StartMessageHandler extends AbstractMessageHandler implements MessageHandler {

    public StartMessageHandler(MessageService messageService, UserRepository userRepository) {
        super(messageService, userRepository);
    }

    @Override
    public SendMessage handle(Message message) {
        return messageService.createGreetingMessage(getUserFromMessage(message));
    }
}
