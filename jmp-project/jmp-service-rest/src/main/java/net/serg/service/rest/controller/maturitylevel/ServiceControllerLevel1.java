package net.serg.service.rest.controller.maturitylevel;

import lombok.RequiredArgsConstructor;
import net.serg.dto.SubscriptionRequestDto;
import net.serg.dto.SubscriptionResponseDto;
import net.serg.service.api.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/level1/subscriptions")
@RequiredArgsConstructor
public class ServiceControllerLevel1 {

    private final SubscriptionService subscriptionService;

    @PostMapping("/create")
    public ResponseEntity<SubscriptionResponseDto> createSubscription(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        var subscriptionResponseDto = subscriptionService.createSubscription(subscriptionRequestDto);
        var uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(subscriptionResponseDto.getId())
            .toUri();
        return ResponseEntity
            .created(uri)
            .body(subscriptionResponseDto);
    }

    @PostMapping("/update")
    public ResponseEntity<SubscriptionResponseDto> updateSubscription(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        var subscriptionResponseDto = subscriptionService.updateSubscription(subscriptionRequestDto);
        return ResponseEntity.ok(subscriptionResponseDto);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @PostMapping("/{id}/get")
    public ResponseEntity<SubscriptionResponseDto> getSubscription(@PathVariable Long id) {
        var subscriptionResponseDto = subscriptionService.getSubscription(id);
        return ResponseEntity.ok(subscriptionResponseDto);
    }

    @PostMapping("/getall")
    public ResponseEntity<List<SubscriptionResponseDto>> getAllSubscription() {
        var subscriptionResponseDtos = subscriptionService.getAllSubscription();
        return ResponseEntity.ok(subscriptionResponseDtos);
    }
}