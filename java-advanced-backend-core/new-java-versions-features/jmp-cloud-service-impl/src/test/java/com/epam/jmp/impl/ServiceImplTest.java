package com.epam.jmp.impl;

import com.epam.jmp.dto.*;
import com.epam.jmp.service.Service;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceImplTest {
    
    @Test
    void testSubscribeAndGetAllUsers() {
        var service = new ServiceImpl();
        var user = new User("Test", "User", LocalDate.of(2000, 1, 1));
        var card = new CreditBankCard("123", user, 1000.0);
        service.subscribe(card);

        var users = service.getAllUsers();
        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
    }

    @Test
    void testGetAverageUsersAge() {
        var service = new ServiceImpl();
        service.subscribe(new CreditBankCard("1", new User("A", "B", LocalDate.now().minusYears(20)), 1000.0));
        service.subscribe(new DebitBankCard("2", new User("C", "D", LocalDate.now().minusYears(30)), 0.0));
        assertTrue(service.getAverageUsersAge() > 0);
    }

    @Test
    void testIsPayableUser() {
        var user = new User("A", "B", LocalDate.now().minusYears(19));
        assertTrue(Service.isPayableUser(user));
    }

    @Test
    void testSubscriptionNotFoundException() {
        var service = new ServiceImpl();
        assertThrows(SubscriptionNotFoundException.class, () -> {
            service.getSubscriptionByBankCardNumber("not-exist")
                   .orElseThrow(() -> new SubscriptionNotFoundException("Not found!"));
        });
    }
}