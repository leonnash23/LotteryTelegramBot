package com.leonid.LotteryBot.validation;

import com.leonid.LotteryBot.domain.UserBalance;
import com.leonid.LotteryBot.exception.NotEnoughMoneyException;

public class BetUserValidator {

    public void validate(UserBalance userBalance, Double bet) throws NotEnoughMoneyException {

        if (userBalance.getMoney() < bet) {
            throw new NotEnoughMoneyException();
        }
    }
}
