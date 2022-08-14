package com.fleetmanagement.api.handler.error;

import com.fleetmanagement.infrastructure.error.GenericError;
import com.fleetmanagement.infrastructure.exception.CoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
@RequiredArgsConstructor
public class ErrorContractBuilder {

    private final ResourceBundleMessageSource resourceBundleMessageSource;

    public ErrorContract build(final CoreException coreException) {
        return ErrorContract.builder()
            .code(buildCode(coreException.getCode()))
            .message(!StringUtils.hasText(coreException.getMessage()) ? getResourceMessage(coreException.getCode())
                : coreException.getMessage())
            .build();
    }

    public ErrorContract build(final String code) {
        return ErrorContract.builder()
            .code(buildCode(code))
            .message(getResourceMessage(code))
            .build();
    }

    private String getResourceMessage(String resourceKey) {
        if (!StringUtils.hasText(resourceKey)) {
            resourceKey = GenericError.CODE;
        }
        String message;
        try {
            message = resourceBundleMessageSource.getMessage(resourceKey, null, LocaleContextHolder.getLocale());
        } catch (Exception exception) {
            message = resourceBundleMessageSource.getMessage(GenericError.CODE, null, LocaleContextHolder.getLocale());
        }
        return message;

    }

    private String buildCode(String code) {
        if (!StringUtils.hasText(code)) {
            code = GenericError.CODE;
        }
        return code;
    }

}
