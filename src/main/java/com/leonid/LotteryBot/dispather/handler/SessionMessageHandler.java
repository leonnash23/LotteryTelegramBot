package com.leonid.LotteryBot.dispather.handler;

import com.leonid.LotteryBot.db.UserRepository;
import com.leonid.LotteryBot.dispather.handler.room.CreateRoomMessageHandler;
import com.leonid.LotteryBot.domain.UserState;
import com.leonid.LotteryBot.service.MessageService;
import com.leonid.LotteryBot.service.RoomService;
import com.leonid.LotteryBot.service.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class SessionMessageHandler extends AbstractMessageHandler {

    protected final RoomService roomService;
    protected final UserSessionManager userSessionManager;
    protected final CreateRoomMessageHandler createRoomMessageHandler;

    protected SessionMessageHandler(MessageService messageService,
                                    UserRepository userRepository,
                                    RoomService roomService,
                                    UserSessionManager userSessionManager,
                                    CreateRoomMessageHandler createRoomMessageHandler) {
        super(messageService, userRepository);
        this.roomService = roomService;
        this.userSessionManager = userSessionManager;
        this.createRoomMessageHandler = createRoomMessageHandler;
    }

    @Override
    public SendMessage handle(Message message) {
        UserState userState = userSessionManager.getUserState(getUserFromMessage(message));
        if (userState == null) {
            return null;
        }
        if (userState == UserState.WAIT_FOR_ROOM_BET) {
            return createRoomMessageHandler.handle(message);
        }
        return null;
    }
}
