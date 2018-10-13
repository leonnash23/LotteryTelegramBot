package com.leonid.LotteryBot.dispather.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {

    SendMessage handle(Message message);
}
