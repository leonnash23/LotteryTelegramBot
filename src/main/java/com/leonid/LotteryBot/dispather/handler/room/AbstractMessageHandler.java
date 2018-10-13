package com.leonid.LotteryBot.dispather.handler.room;

import com.leonid.LotteryBot.db.UserRepository;
import com.leonid.LotteryBot.domain.Room;
import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.service.MessageService;
import com.leonid.LotteryBot.service.RoomService;
import com.leonid.LotteryBot.service.UserSessionManager;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class AbstractMessageHandler extends AbstractRoomMessageHandler {


    protected AbstractMessageHandler(MessageService messageService, UserRepository userRepository, RoomService roomService, UserSessionManager userSessionManager) {
        super(messageService, userRepository, roomService, userSessionManager);
    }

    @Override
    public SendMessage handle(Message message) {
        return createAnswer(getUserFromMessage(message), getOpenRooms());
    }

    private List<Room> getOpenRooms() {
        return roomService.getOpenRoom();
    }

    @Override
    protected SendMessage createMessageForExistsRoom(User user, List<Room> rooms) {
        return messageService.createTextMessage(user, createRoomsInfo(rooms));
    }

    private String createRoomsInfo(List<Room> rooms) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Room room : rooms) {
            stringBuilder
                    .append("/join")
                    .append(room.getId())
                    .append(" Ставка: ")
                    .append(room.getBet())
                    .append(", Пользователей: ")
                    .append(room.getUsers().size())
                    .append("/6")
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
