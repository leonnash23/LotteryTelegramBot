package com.leonid.LotteryBot.dispather;

import com.leonid.LotteryBot.dispather.handler.MessageHandler;
import com.leonid.LotteryBot.dispather.handler.RoomManagementHandler;
import com.leonid.LotteryBot.dispather.handler.SessionMessageHandler;
import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.service.UserService;
import com.leonid.LotteryBot.service.UserSessionManager;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

public class MessageDispatcher {

    private final Map<String, MessageHandler> handlers;
    private final SessionMessageHandler sessionMessageHandler;
    private final UserSessionManager userSessionManager;
    private final UserService userService;
    private final RoomManagementHandler roomManagementHandler;

    public MessageDispatcher(Map<String, MessageHandler> handlers,
                             SessionMessageHandler sessionMessageHandler,
                             UserSessionManager userSessionManager, UserService userService, RoomManagementHandler roomManagementHandler) {
        this.handlers = handlers;
        this.sessionMessageHandler = sessionMessageHandler;
        this.userSessionManager = userSessionManager;
        this.userService = userService;
        this.roomManagementHandler = roomManagementHandler;
    }

    public SendMessage dispatch(Message message) {
        String trimMessage = message.getText().toLowerCase().trim();
        if (message.hasText()) {
            MessageHandler messageHandler = handlers.get(trimMessage);
            if (messageHandler != null) {
                userSessionManager.resetUserState(getUser(message));
                return messageHandler.handle(message);
            } else if (trimMessage.startsWith("/exit")
                    || trimMessage.startsWith("/join")) {
                return roomManagementHandler.handle(message);
            } else {
                return sessionMessageHandler.handle(message);
            }

        }
        return null;
    }

    private User getUser(Message message) {
        return userService.getUserFromMessage(message);
    }

}
