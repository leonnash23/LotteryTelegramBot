package com.leonid.LotteryBot.service;


import com.leonid.LotteryBot.db.UserRepository;
import com.leonid.LotteryBot.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean isUserExist(Long uid) {
        return userRepository.findByUid(uid) != null;
    }

    public void createAndSaveUser(Long uid, String username) {
        User user = User.createUser(uid, username);
        user = userRepository.save(user);
    }

    public User findUserByUid(Long uid) {
        return userRepository.findByUid(uid);
    }

    public User getUserFromMessage(Message message) {
        return userRepository.findByUid(message.getChatId());
    }
}
