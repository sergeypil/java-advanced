package net.serg.cloud.service.impl.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import net.serg.cloud.service.impl.entity.Subscription;
import net.serg.dto.SubscriptionRequestDto;

import java.time.LocalDate;

@Component
public class SubscriptionRequestDtoToSubscriptionConverter implements Converter<SubscriptionRequestDto, Subscription> {

    @Override
    public Subscription convert(SubscriptionRequestDto source) {
        return Subscription
            .builder()
            .id(source.getId())
            .startDate(LocalDate.parse(source.getStartDate()))
            .build();
    }
}