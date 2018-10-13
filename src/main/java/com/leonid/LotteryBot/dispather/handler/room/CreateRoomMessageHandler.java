package com.leonid.LotteryBot.dispather.handler.room;

import com.leonid.LotteryBot.db.UserRepository;
import com.leonid.LotteryBot.dispather.handler.AbstractMessageHandler;
import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.service.MessageService;
import com.leonid.LotteryBot.service.RoomService;
import com.leonid.LotteryBot.service.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class CreateRoomMessageHandler extends AbstractMessageHandler {

    private final RoomService roomService;
    private final UserSessionManager userSessionManager;

    protected CreateRoomMessageHandler(MessageService messageService, UserRepository userRepository, RoomService roomService, UserSessionManager userSessionManager, RoomService roomService1, UserSessionManager userSessionManager1) {
        super(messageService, userRepository);
        this.roomService = roomService1;
        this.userSessionManager = userSessionManager1;
    }

    @Override
    public SendMessage handle(Message message) {
        User user = getUserFromMessage(message);
        roomService.createAndSave(user,
                Double.parseDouble(getLowerAndTrimText(message)));
        userSessionManager.resetUserState(user);
        return messageService.createRoomCreatedMessage(user);
    }
}
