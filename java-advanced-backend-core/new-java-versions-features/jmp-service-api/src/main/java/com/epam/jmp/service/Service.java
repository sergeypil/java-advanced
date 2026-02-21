package com.epam.jmp.service;

import com.epam.jmp.dto.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {
    void subscribe(BankCard bankCard);
    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);
    List<User> getAllUsers();
    
    default double getAverageUsersAge() {
        return getAllUsers().stream()
                            .mapToDouble(user -> ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()))
                            .average()
                            .orElse(0.0);
    }
    
    static boolean isPayableUser(User user) {
        return ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()) >= 18;
    }
    
    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate);
}