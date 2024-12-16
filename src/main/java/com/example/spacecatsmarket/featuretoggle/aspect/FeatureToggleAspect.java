package com.example.spacecatsmarket.featuretoggle.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.example.spacecatsmarket.featuretoggle.FeatureToggles;
import com.example.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import com.example.spacecatsmarket.featuretoggle.exception.FeatureToggleNotEnabledException;
import com.example.spacecatsmarket.featuretoggle.service.FeatureToggleService;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Around(value = "@annotation(featureToggle)")
    public Object checkFeatureToggleAnnotation(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        return checkToggle(joinPoint, featureToggle);
    }

    private Object checkToggle(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        FeatureToggles toggle = featureToggle.value();

        if (featureToggleService.check(toggle.getFeatureName())) {
            return joinPoint.proceed();
        }
        log.warn("Feature toggle {} is not enabled!", toggle.getFeatureName());
        throw new FeatureToggleNotEnabledException(toggle.getFeatureName());
    }

}
