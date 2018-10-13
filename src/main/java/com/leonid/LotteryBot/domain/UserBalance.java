package com.leonid.LotteryBot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class UserBalance extends AbstractEntity {

    @Column
    private double money;

    @OneToOne(mappedBy = "userBalance")
    private User user;

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
