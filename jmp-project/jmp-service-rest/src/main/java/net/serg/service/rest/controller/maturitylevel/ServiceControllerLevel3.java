package net.serg.service.rest.controller.maturitylevel;

import lombok.RequiredArgsConstructor;
import net.serg.dto.SubscriptionRequestDto;
import net.serg.dto.SubscriptionResponseDto;
import net.serg.service.api.SubscriptionService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/level3/subscriptions")
@RequiredArgsConstructor
public class ServiceControllerLevel3 {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<EntityModel<SubscriptionResponseDto>> createSubscription(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        var subscriptionResponseDto = subscriptionService.createSubscription(subscriptionRequestDto);

        Link selfLink = WebMvcLinkBuilder
            .linkTo(methodOn(ServiceControllerLevel3.class).getSubscription(subscriptionResponseDto.getId()))
            .withSelfRel();
        Link deleteLink = WebMvcLinkBuilder
            .linkTo(methodOn(ServiceControllerLevel3.class).deleteSubscription(subscriptionResponseDto.getId()))
            .withRel("delete");
        Link updateLink = WebMvcLinkBuilder
            .linkTo(methodOn(ServiceControllerLevel3.class).updateSubscription(subscriptionRequestDto))
            .withRel("update");

        var uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(subscriptionResponseDto.getId())
            .toUri();

        return ResponseEntity
            .created(uri)
            .body(EntityModel.of(subscriptionResponseDto, selfLink, deleteLink, updateLink));
    }

    @PutMapping
    public ResponseEntity<EntityModel<SubscriptionResponseDto>> updateSubscription(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        var subscriptionResponseDto = subscriptionService.updateSubscription(subscriptionRequestDto);

        Link selfLink = WebMvcLinkBuilder
            .linkTo(methodOn(ServiceControllerLevel3.class).getSubscription(subscriptionResponseDto.getId()))
            .withSelfRel();
        Link deleteLink = WebMvcLinkBuilder
            .linkTo(methodOn(ServiceControllerLevel3.class).deleteSubscription(subscriptionResponseDto.getId()))
            .withRel("delete");

        return ResponseEntity.ok(EntityModel.of(subscriptionResponseDto, selfLink, deleteLink));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SubscriptionResponseDto>> getSubscription(@PathVariable Long id) {
        var subscriptionResponseDto = subscriptionService.getSubscription(id);

        Link deleteLink = WebMvcLinkBuilder
            .linkTo(methodOn(ServiceControllerLevel3.class).deleteSubscription(id))
            .withRel("delete");
        Link updateLink = WebMvcLinkBuilder
            .linkTo(methodOn(ServiceControllerLevel3.class).updateSubscription(new SubscriptionRequestDto()))
            .withRel("update");

        return ResponseEntity.ok(EntityModel.of(subscriptionResponseDto, deleteLink, updateLink));
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<SubscriptionResponseDto>>> getAllSubscription() {
        var subscriptionResponseDtos = subscriptionService.getAllSubscription();
        var subscriptions = subscriptionResponseDtos
            .stream()
            .map(subscription -> EntityModel.of(
                subscription,
                WebMvcLinkBuilder
                    .linkTo(methodOn(
                        ServiceControllerLevel3.class).getSubscription(subscription.getId()))
                    .withSelfRel(),
                WebMvcLinkBuilder
                    .linkTo(methodOn(
                        ServiceControllerLevel3.class).deleteSubscription(subscription.getId()))
                    .withRel("delete"),
                WebMvcLinkBuilder
                    .linkTo(methodOn(
                        ServiceControllerLevel3.class).updateSubscription(new SubscriptionRequestDto()))
                    .withRel("update")))
            .collect(Collectors.toList());

        return ResponseEntity.ok(subscriptions);
    }
}