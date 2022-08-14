package com.fleetmanagement.adapter.localization;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class LocalizationConfiguration {

    @Bean
    public ResourceBundleMessageSource messageSource() {
        Locale.setDefault(new Locale("tr","TR"));
        final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("i18n/message");
        source.setDefaultEncoding("UTF-8");
        return source;
    }
}
