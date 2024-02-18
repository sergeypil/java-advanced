package net.serg.service.rest.controller.maturitylevel;

import lombok.RequiredArgsConstructor;
import net.serg.dto.SubscriptionRequestDto;
import net.serg.service.api.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/level0/subscriptions")
@RequiredArgsConstructor
public class ServiceControllerLevel0 {
    private final SubscriptionService subscriptionService;
    
    @PostMapping("/{action}")
    public ResponseEntity<?> subscriptionAction(
        @RequestBody SubscriptionRequestDto subscriptionRequestDto,
        @PathVariable String action) {
        switch (action.toLowerCase()) {
            case "create":
                var responseDto = subscriptionService.createSubscription(subscriptionRequestDto);
                var location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(responseDto.getId())
                    .toUri();
                return ResponseEntity
                    .created(location)
                    .body(responseDto);
            case "update":
                var updatedDto = subscriptionService.updateSubscription(subscriptionRequestDto);
                return ResponseEntity.ok(updatedDto);
            case "delete":
                subscriptionService.deleteSubscription(subscriptionRequestDto.getId());
                return ResponseEntity
                    .noContent()
                    .build();
            case "get":
                var getResponseDto = subscriptionService.getSubscription(subscriptionRequestDto.getId());
                return ResponseEntity.ok(getResponseDto);
            case "getall":
                var responseDtos = subscriptionService.getAllSubscription();
                return ResponseEntity.ok(responseDtos);
            default:
                throw new IllegalArgumentException("Invalid action command");
        }
    }
}