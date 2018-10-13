package com.leonid.LotteryBot.service;

import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.domain.UserState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserSessionManager {

    private final Map<User, UserState> userStateMap;

    public UserSessionManager() {
        userStateMap = new HashMap<>();
    }

    public boolean isSessionExist(User user) {
        return userStateMap.containsKey(user);
    }

    public UserState getUserState(User user) {
        return userStateMap.get(user);
    }

    public void resetUserState(User user) {
        userStateMap.remove(user);
    }

    public void addUserWaitForRoomBet(User user) {
        userStateMap.put(user, UserState.WAIT_FOR_ROOM_BET);
    }


}
