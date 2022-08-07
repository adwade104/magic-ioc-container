package com.wade.adam.ioc.config;

public class BaseSubConfig {

    private final MagicConfig parentConfig;

    protected BaseSubConfig(MagicConfig parentConfig) {
        this.parentConfig = parentConfig;
    }

    public MagicConfig and(){
        return parentConfig;
    }

}
