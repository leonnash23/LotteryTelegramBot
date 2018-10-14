package com.leonid.LotteryBot.validation;

import com.leonid.LotteryBot.domain.User;

public interface UserValidator<T> {

    void validate(User user, T toValidate) throws Exception;
}
