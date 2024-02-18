package net.serg.cloud.service.impl.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import net.serg.dto.SubscriptionResponseDto;
import net.serg.cloud.service.impl.entity.Subscription;

@Component
public class SubscriptionToSubscriptionResponseDtoConverter
    implements Converter<Subscription, SubscriptionResponseDto> {

    @Override
    public SubscriptionResponseDto convert(Subscription source) {
        return SubscriptionResponseDto
            .builder()
            .id(source.getId())
            .startDate(source
                           .getStartDate()
                           .toString())
            .userId(source
                        .getUser()
                        .getId())
            .build();
    }
}