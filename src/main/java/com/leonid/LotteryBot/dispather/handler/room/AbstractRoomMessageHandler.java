package com.leonid.LotteryBot.dispather.handler.room;

import com.leonid.LotteryBot.db.UserRepository;
import com.leonid.LotteryBot.dispather.handler.AbstractMessageHandler;
import com.leonid.LotteryBot.domain.Room;
import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.service.MessageService;
import com.leonid.LotteryBot.service.RoomService;
import com.leonid.LotteryBot.service.UserSessionManager;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public abstract class AbstractRoomMessageHandler extends AbstractMessageHandler {

    protected final RoomService roomService;
    protected final UserSessionManager userSessionManager;

    protected AbstractRoomMessageHandler(MessageService messageService, UserRepository userRepository, RoomService roomService, UserSessionManager userSessionManager) {
        super(messageService, userRepository);
        this.roomService = roomService;
        this.userSessionManager = userSessionManager;
    }

    protected SendMessage createAnswer(User user, List<Room> rooms) {
        if (rooms.size() == 0) {
            return createNoRoomsMessage(user);
        } else {
            return createMessageForExistsRoom(user, rooms);
        }
    }

    protected abstract SendMessage createMessageForExistsRoom(User user, List<Room> rooms);

    private SendMessage createNoRoomsMessage(User user) {
        return messageService.createTextMessage(user, "Тут пока пусто");
    }
}
