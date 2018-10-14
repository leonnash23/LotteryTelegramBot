package com.leonid.LotteryBot.validation;

public interface Validator<T> {

    void validate(T toValidate) throws Exception;
}
