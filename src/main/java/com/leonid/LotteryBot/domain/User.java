package com.leonid.LotteryBot.domain;

import javax.persistence.*;

@Entity(name = "user_entity")
public class User extends AbstractEntity {

    public static User createUser(Long uid, String username) {
        User user = new User();
        user.setUid(uid);
        user.setUsername(username);
        user.setUserBalance(new UserBalance());
        return user;
    }

    private User() {
    }

    @Column
    private Long uid;

    @Column
    private String username;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userBalance_id")
    private UserBalance userBalance;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public UserBalance getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(UserBalance userBalance) {
        this.userBalance = userBalance;
    }
}
