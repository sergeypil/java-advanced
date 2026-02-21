package com.epam.jmp;

import com.epam.jmp.bankservice.Bank;
import com.epam.jmp.dto.*;
import com.epam.bankservice.bankimpl.BankImpl;
import com.epam.jmp.impl.ServiceImpl;
import com.epam.jmp.impl.SubscriptionNotFoundException;
import com.epam.jmp.service.Service;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        Bank bank = new BankImpl();
        Service service = new ServiceImpl();

        var user1 = new User("Ryan", "Gosling", LocalDate.of(1990, 1, 1));
        var user2 = new User("Emma", "Stone", LocalDate.of(2000, 5, 15));

        var card1 = bank.createBankCard(user1, BankCardType.CREDIT);
        var card2 = bank.createBankCard(user2, BankCardType.DEBIT);
        
        service.subscribe(card1);
        service.subscribe(card2);

        System.out.println("All users: " + service.getAllUsers());
        System.out.println("Average age: " + service.getAverageUsersAge());
        System.out.println("User1 payable: " + Service.isPayableUser(user1));
        System.out.println("User2 payable: " + Service.isPayableUser(user2));
        
        try {
            service.getSubscriptionByBankCardNumber("not-exist")
                   .orElseThrow(() -> new SubscriptionNotFoundException("Not found!"));
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        
        var subs = service.getAllSubscriptionsByCondition(sub -> sub.getStartDate().isBefore(LocalDate.now().plusDays(1)));
        System.out.println("Filtered subscriptions: " + subs);
    }
}