package com.wade.adam.ioc.config;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomAnnotationConfig extends BaseSubConfig {

    private final Set<Class<? extends Annotation>> customServiceAnnotations;

    private final Set<Class<? extends Annotation>> customBeanAnnotations;

    protected CustomAnnotationConfig(MagicConfig parentConfig) {
        super(parentConfig);
        this.customServiceAnnotations = new HashSet<>();
        this.customBeanAnnotations = new HashSet<>();
    }

    public CustomAnnotationConfig addCustomServiceAnnotation(Class<? extends Annotation> annotation){
        customServiceAnnotations.add(annotation);
        return this;
    }

    public CustomAnnotationConfig addCustomServiceAnnotations(Class<? extends Annotation>[] annotations){
        customServiceAnnotations.addAll(Arrays.asList(annotations));
        return this;
    }

    public CustomAnnotationConfig addCustomBeanAnnotation(Class<? extends Annotation> annotation){
        customBeanAnnotations.add(annotation);
        return this;
    }

    public CustomAnnotationConfig addCustomBeanAnnotations(Class<? extends Annotation>[] annotations){
        customBeanAnnotations.addAll(Arrays.asList(annotations));
        return this;
    }

    public Set<Class<? extends Annotation>> getCustomServiceAnnotations() {
        return customServiceAnnotations;
    }

    public Set<Class<? extends Annotation>> getCustomBeanAnnotations() {
        return customBeanAnnotations;
    }
}
