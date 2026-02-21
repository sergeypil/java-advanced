package com.epam.bankservice.bankimpl;

import com.epam.jmp.dto.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BankImplTest {

    @Test
    void createBankCard_shouldReturnCreditBankCard_whenTypeIsCredit() {
        BankImpl bank = new BankImpl();
        User user = new User("Ivan", "Ivanov", LocalDate.of(1990, 1, 1));

        BankCard card = bank.createBankCard(user, BankCardType.CREDIT);

        assertNotNull(card);
        assertInstanceOf(CreditBankCard.class, card);
        assertEquals(user, card.getUser());
        assertNotNull(card.getNumber());
        assertFalse(card.getNumber().isEmpty());

        CreditBankCard creditCard = (CreditBankCard) card;
        assertEquals(10000.0, creditCard.getCreditLimit());
    }

    @Test
    void createBankCard_shouldReturnDebitBankCard_whenTypeIsDebit() {
        BankImpl bank = new BankImpl();
        User user = new User("Petr", "Petrov", LocalDate.of(1985, 5, 15));

        BankCard card = bank.createBankCard(user, BankCardType.DEBIT);

        assertNotNull(card);
        assertInstanceOf(DebitBankCard.class, card);
        assertEquals(user, card.getUser());
        assertNotNull(card.getNumber());
        assertFalse(card.getNumber().isEmpty());

        DebitBankCard debitCard = (DebitBankCard) card;
        assertEquals(0.0, debitCard.getBalance());
    }

    @Test
    void createBankCard_shouldGenerateUniqueNumbers() {
        BankImpl bank = new BankImpl();
        User user = new User("Test", "User", LocalDate.of(2000, 1, 1));

        BankCard card1 = bank.createBankCard(user, BankCardType.CREDIT);
        BankCard card2 = bank.createBankCard(user, BankCardType.CREDIT);

        assertNotEquals(card1.getNumber(), card2.getNumber());
    }
}