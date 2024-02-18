package net.serg.cloud.service.impl.service;

import lombok.RequiredArgsConstructor;
import net.serg.cloud.service.impl.entity.Subscription;
import net.serg.cloud.service.impl.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionDataService {
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Transactional(readOnly = true)
    public Optional<Subscription> getSubscription(Long id) {
        return subscriptionRepository.findById(id);
    }
    
    @Transactional
    public void deleteById(Long id) {
        subscriptionRepository.deleteById(id);
    }

    @Transactional
    public List<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }
}
