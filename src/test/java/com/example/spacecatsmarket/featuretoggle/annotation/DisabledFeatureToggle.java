package com.example.spacecatsmarket.featuretoggle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.spacecatsmarket.featuretoggle.FeatureToggles;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DisabledFeatureToggle {

  FeatureToggles value();
  
}
