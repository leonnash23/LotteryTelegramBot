package com.leonid.LotteryBot.config;

import com.leonid.LotteryBot.dispather.MessageDispatcher;
import com.leonid.LotteryBot.dispather.handler.*;
import com.leonid.LotteryBot.dispather.handler.room.AbstractMessageHandler;
import com.leonid.LotteryBot.dispather.handler.room.NewRoomMessageHandler;
import com.leonid.LotteryBot.dispather.handler.room.UserRoomsMessageHandler;
import com.leonid.LotteryBot.service.UserService;
import com.leonid.LotteryBot.service.UserSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Configuration
public class MainHandlerConfig {

    @Autowired
    private StartMessageHandler startMessageHandler;
    @Autowired
    private BalanceMessageHandler balanceMessageHandler;
    @Autowired
    private NewRoomMessageHandler newRoomMessageHandler;
    @Autowired
    private AbstractMessageHandler roomsMessageHandler;
    @Autowired
    private UserRoomsMessageHandler userRoomsMessageHandler;
    @Autowired
    private CancelMessageHandler cancelMessageHandler;


    @Bean
    public MessageDispatcher createMessageDispatcher(SessionMessageHandler sessionMessageHandler,
                                                     UserSessionManager userSessionManager,
                                                     UserService userService,
                                                     RoomManagementHandler roomManagementHandler) {
        Map<String, MessageHandler> handlerMap = initHandlers();
        return new MessageDispatcher(
                handlerMap,
                sessionMessageHandler,
                userSessionManager,
                userService,
                roomManagementHandler);
    }

    private Map<String, MessageHandler> initHandlers() {
        Map<String, MessageHandler> handlerMap = new HashMap<>();
        handlerMap.put("/start", startMessageHandler);
        handlerMap.put("/balance", balanceMessageHandler);
        handlerMap.put("/newroom", newRoomMessageHandler);
        handlerMap.put("/rooms", roomsMessageHandler);
        handlerMap.put("/myrooms", userRoomsMessageHandler);
        handlerMap.put("/cancel", cancelMessageHandler);
        return handlerMap;
    }

}
