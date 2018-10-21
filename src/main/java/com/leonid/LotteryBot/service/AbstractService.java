package com.leonid.LotteryBot.service;

import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;

public abstract class AbstractService {

    protected final SessionFactory sessionFactory;

    protected AbstractService(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }
}
