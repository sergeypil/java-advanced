package com.epam.bankservice.bankimpl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.DebitBankCard;
import com.epam.jmp.dto.User;
import com.epam.jmp.bankservice.Bank;

import java.util.UUID;

public class BankImpl implements Bank {
    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        var cardNumber = UUID.randomUUID().toString();
        return switch (cardType) {
            case CREDIT -> createCard(CreditBankCard::new, cardNumber, user, 10000.0);
            case DEBIT -> createCard(DebitBankCard::new, cardNumber, user, 0.0);
        };
    }

    private <T extends BankCard> T createCard(CardCreator<T> creator, String number, User user, double value) {
        return creator.create(number, user, value);
    }

    @FunctionalInterface
    interface CardCreator<T extends BankCard> {
        T create(String number, User user, double value);
    }
    
//    @Override
//    public BankCard createBankCard(User user, BankCardType cardType) {
//        var cardNumber = UUID
//            .randomUUID().toString();
//        return switch (cardType) {
//            case CREDIT -> new CreditBankCard(cardNumber, user, 10000.0);
//            case DEBIT -> new DebitBankCard(cardNumber, user, 0.0);
//        };
//    }
}