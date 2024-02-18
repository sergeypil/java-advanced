package net.serg.service.api;

import net.serg.dto.SubscriptionRequestDto;
import net.serg.dto.SubscriptionResponseDto;

import java.util.List;

public interface SubscriptionService {
    SubscriptionResponseDto createSubscription(SubscriptionRequestDto subscriptionRequestDto);
    SubscriptionResponseDto updateSubscription(SubscriptionRequestDto subscriptionRequestDto);
    void deleteSubscription(Long id);
    SubscriptionResponseDto getSubscription(Long id);
    List<SubscriptionResponseDto> getAllSubscription();
}