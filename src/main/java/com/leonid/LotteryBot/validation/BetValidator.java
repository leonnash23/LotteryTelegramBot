package com.leonid.LotteryBot.validation;

import com.leonid.LotteryBot.exception.NegativeBetException;

public class BetValidator implements Validator<String> {

    @Override
    public void validate(String toValidate) throws NumberFormatException,
            NegativeBetException {

        double bet = Double.parseDouble(toValidate);
        if (bet < 0) {
            throw new NegativeBetException();
        }
    }
}
