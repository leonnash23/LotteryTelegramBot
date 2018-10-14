package com.leonid.LotteryBot.validation;

import com.leonid.LotteryBot.domain.User;
import com.leonid.LotteryBot.exception.NotEnoughMoneyException;

public class BetUserValidator implements UserValidator<Double> {


    @Override
    public void validate(User user, Double bet) throws NotEnoughMoneyException {

        if (user.getUserBalance().getMoney() < bet) {
            throw new NotEnoughMoneyException();
        }
    }
}
