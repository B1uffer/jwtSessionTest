package com.example.demo.config;

import org.springframework.aop.Advisor;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

public class TxConfig {
    // jakarta 아님
    private final TransactionManager transactionManager;

    public TxConfig(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Bean
    public TransactionInterceptor txAdvice() {
        return null;
    }

    @Bean
    public Advisor txAdvisor() {
        return null;
    }
}
