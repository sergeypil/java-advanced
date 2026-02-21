package com.epam.jmp.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.Service;

import java.util.*;
import java.util.function.Predicate;

public class ServiceImpl implements Service {
    private final List<Subscription> subscriptions = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    @Override
    public void subscribe(BankCard bankCard) {
        var subscription = new Subscription(bankCard.getNumber(), java.time.LocalDate.now());
        subscriptions.add(subscription);
        users.add(bankCard.getUser());
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return subscriptions.stream()
                            .filter(sub -> sub.getBankcard().equals(cardNumber))
                            .findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return users.stream().distinct().toList();
    }
    
    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate) {
        return subscriptions.stream()
                            .filter(predicate)
                            .toList();
    }
}