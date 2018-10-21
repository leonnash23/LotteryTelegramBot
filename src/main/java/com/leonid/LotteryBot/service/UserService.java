package com.leonid.LotteryBot.service;


import com.leonid.LotteryBot.db.UserRepository;
import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.domain.UserBalance;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.EntityManagerFactory;

@Component
public class UserService extends AbstractService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, EntityManagerFactory factory) {
        super(factory);
        this.userRepository = userRepository;
    }


    public boolean isUserExist(Long uid) {
        return userRepository.findByUid(uid) != null;
    }

    public void createAndSaveUser(Long uid, String username) {
        User user = User.createUser(uid, username);
        userRepository.save(user);
    }

    public User findUserByUid(Long uid) {
        return userRepository.findByUid(uid);
    }

    public User getUserFromMessage(Message message) {
        return userRepository.findByUid(message.getChatId());
    }

    public UserBalance getUserBalance(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.load(user, user.getId());
        UserBalance userBalance = user.getUserBalance();
        session.getTransaction().commit();
        return userBalance;
    }

}
