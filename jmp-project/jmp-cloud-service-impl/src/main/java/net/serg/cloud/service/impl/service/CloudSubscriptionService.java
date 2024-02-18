package net.serg.cloud.service.impl.service;

import lombok.RequiredArgsConstructor;
import net.serg.cloud.service.impl.converter.SubscriptionRequestDtoToSubscriptionConverter;
import net.serg.cloud.service.impl.converter.SubscriptionToSubscriptionResponseDtoConverter;
import net.serg.cloud.service.impl.entity.Subscription;
import net.serg.dto.SubscriptionRequestDto;
import net.serg.dto.SubscriptionResponseDto;
import net.serg.service.api.SubscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CloudSubscriptionService implements SubscriptionService {

    private final SubscriptionDataService subscriptionDataService;
    private final UserDataService userDataService;
    private final SubscriptionRequestDtoToSubscriptionConverter subscriptionRequestDtoToSubscriptionConverter;
    private final SubscriptionToSubscriptionResponseDtoConverter subscriptionToSubscriptionResponseDtoConverter;

    @Override
    @Transactional
    public SubscriptionResponseDto createSubscription(SubscriptionRequestDto subscriptionRequestDto) {
        var user = userDataService
            .getUser(subscriptionRequestDto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        var subscription = subscriptionRequestDtoToSubscriptionConverter.convert(subscriptionRequestDto);
        if (subscription != null) {
            subscription.setUser(user);
        }
        var savedSubscription = subscriptionDataService.save(subscription);
        return subscriptionToSubscriptionResponseDtoConverter.convert(savedSubscription);
    }

    @Override
    @Transactional
    public SubscriptionResponseDto updateSubscription(SubscriptionRequestDto subscriptionRequestDto) {
        var subscription = subscriptionDataService
            .getSubscription(subscriptionRequestDto.getId())
            .orElseThrow(() -> new RuntimeException("Subscription not found"));
        subscription.setStartDate(LocalDate.parse(subscriptionRequestDto.getStartDate()));
        var updatedSubscription = subscriptionDataService.save(subscription);
        return subscriptionToSubscriptionResponseDtoConverter.convert(updatedSubscription);
    }

    @Override
    @Transactional
    public void deleteSubscription(Long id) {
        subscriptionDataService.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SubscriptionResponseDto getSubscription(Long id) {
        Subscription subscription = subscriptionDataService
            .getSubscription(id)
            .orElseThrow(() -> new RuntimeException("Subscription not found"));
        return subscriptionToSubscriptionResponseDtoConverter.convert(subscription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponseDto> getAllSubscription() {
        return subscriptionDataService
            .getAll()
            .stream()
            .map(subscriptionToSubscriptionResponseDtoConverter::convert)
            .toList();
    }
}