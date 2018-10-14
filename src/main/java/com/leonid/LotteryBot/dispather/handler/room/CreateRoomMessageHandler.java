package com.leonid.LotteryBot.dispather.handler.room;

import com.leonid.LotteryBot.db.UserRepository;
import com.leonid.LotteryBot.dispather.handler.AbstractMessageHandler;
import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.exception.NegativeBetException;
import com.leonid.LotteryBot.exception.NotEnoughMoneyException;
import com.leonid.LotteryBot.service.MessageService;
import com.leonid.LotteryBot.service.RoomService;
import com.leonid.LotteryBot.service.UserSessionManager;
import com.leonid.LotteryBot.validation.BetUserValidator;
import com.leonid.LotteryBot.validation.BetValidator;
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
        String lowerAndTrimText = getLowerAndTrimText(message);
        String answer;
        try {
            new BetValidator().validate(lowerAndTrimText);
            double bet = Double.parseDouble(lowerAndTrimText);
            new BetUserValidator().validate(user, bet);
            roomService.createAndSave(user, bet);
            userSessionManager.resetUserState(user);
            answer = "Комната создана";
        } catch (NegativeBetException e) {
            answer = "Ставка не может быть отрицательной";
        } catch (NumberFormatException e) {
            answer = "Некорректное число";
        } catch (NotEnoughMoneyException e) {
            answer = "На вашем счету недостаточно средств";
        }
        return messageService.createTextMessage(user, answer);
    }
}
