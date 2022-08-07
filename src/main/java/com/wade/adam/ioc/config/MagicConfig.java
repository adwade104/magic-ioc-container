package com.wade.adam.ioc.config;

public class MagicConfig {

    private final CustomAnnotationConfig annotations;

    public MagicConfig() {
        this.annotations = new CustomAnnotationConfig(this);
    }

    public CustomAnnotationConfig annotations(){
        return annotations;
    }

    public MagicConfig build(){
        return this;
    }

}
