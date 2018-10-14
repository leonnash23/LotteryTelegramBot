package com.leonid.LotteryBot.dispather.handler;

import com.leonid.LotteryBot.db.UserRepository;
import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.exception.AlreadyOnRoomException;
import com.leonid.LotteryBot.exception.FullRoomException;
import com.leonid.LotteryBot.exception.NotOpenRoomException;
import com.leonid.LotteryBot.exception.RoomNotFoundException;
import com.leonid.LotteryBot.service.MessageService;
import com.leonid.LotteryBot.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class RoomManagementHandler extends AbstractMessageHandler {

    private final RoomService roomService;

    @Autowired
    protected RoomManagementHandler(MessageService messageService, UserRepository userRepository, RoomService roomService) {
        super(messageService, userRepository);
        this.roomService = roomService;
    }

    @Override
    public SendMessage handle(Message message) {
        String cleanText = getLowerAndTrimText(message);
        User user = getUserFromMessage(message);
        try {
            if (cleanText.startsWith("/join")) {
                return tryToJoinRoom(cleanText, user);
            } else if (cleanText.startsWith("/exit")) {
                return tryToExitFromRoom(cleanText, user);
            } else {
                return messageService.createTextMessage(user, "Неизвестная команда");
            }
        } catch (NumberFormatException e) {
            return messageService.createTextMessage(user, "Некорректный номер комнаты");
        }
    }

    private SendMessage tryToExitFromRoom(String cleanText, User user) {
        SendMessage answer;
        try {
            roomService.removeUserFromRoomByIf(user, getRoomId(cleanText));
            answer = messageService.createTextMessage(user, "Вы покинули комнату");
        } catch (RoomNotFoundException e) {
            answer = messageService.createTextMessage(user, "Комната не найдена");
        }
        return answer;
    }

    private SendMessage tryToJoinRoom(String cleanText, User user) {
        String answer;
        try {
            roomService.addUserToRoomById(user, getRoomId(cleanText));
            answer = "Вы присоединились к комнате";
        } catch (FullRoomException e) {
            answer = "Комната заполнена";
        } catch (RoomNotFoundException e) {
            answer = "Комната не найдена";
        } catch (NotOpenRoomException e) {
            answer = "К этой комнате нельзя присоединиться";
        } catch (AlreadyOnRoomException e) {
            answer = "Вы уже в этой комнате";
        }
        return messageService.createTextMessage(user, answer);
    }

    private Long getRoomId(String cleanText) {
        String roomId = cleanText.replace("/join", "");
        roomId = roomId.replace("/exit", "");
        return Long.valueOf(roomId);
    }


}
