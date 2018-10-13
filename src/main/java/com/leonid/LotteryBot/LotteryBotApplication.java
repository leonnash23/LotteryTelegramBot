package com.leonid.LotteryBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.telegram.telegrambots.ApiContextInitializer;


@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
public class LotteryBotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(LotteryBotApplication.class, args);
    }
}
