package com.leonid.LotteryBot.dispather.handler;

import com.leonid.LotteryBot.db.UserRepository;
import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.service.MessageService;
import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class AbstractMessageHandler implements MessageHandler {

    protected final MessageService messageService;
    protected final UserRepository userRepository;

    protected AbstractMessageHandler(MessageService messageService, UserRepository userRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    protected User getUserFromMessage(Message message) {
        return userRepository.findByUid(message.getChatId());
    }

    protected String getLowerAndTrimText(Message message) {
        return message.getText().toLowerCase().trim();
    }
}
