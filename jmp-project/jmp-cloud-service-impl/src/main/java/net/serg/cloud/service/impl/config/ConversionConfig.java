package net.serg.cloud.service.impl.config;

import lombok.RequiredArgsConstructor;
import net.serg.cloud.service.impl.converter.SubscriptionRequestDtoToSubscriptionConverter;
import net.serg.cloud.service.impl.converter.SubscriptionToSubscriptionResponseDtoConverter;
import net.serg.cloud.service.impl.converter.UserRequestDtoToUserConverter;
import net.serg.cloud.service.impl.converter.UserToUserResponseDtoConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

@Configuration
@RequiredArgsConstructor
public class ConversionConfig extends FormattingConversionServiceFactoryBean {

    private final UserRequestDtoToUserConverter userRequestDtoToUserConverter;
    private final UserToUserResponseDtoConverter userToUserResponseDtoConverter;
    private final SubscriptionToSubscriptionResponseDtoConverter subscriptionToSubscriptionResponseDtoConverter;
    private final SubscriptionRequestDtoToSubscriptionConverter subscriptionRequestDtoToSubscriptionConverter;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        FormatterRegistry registry = this.getObject();
        if (registry != null) {
            registry.addConverter(userRequestDtoToUserConverter);
            registry.addConverter(userToUserResponseDtoConverter);
            registry.addConverter(subscriptionToSubscriptionResponseDtoConverter);
            registry.addConverter(subscriptionRequestDtoToSubscriptionConverter);
        }
    }
}