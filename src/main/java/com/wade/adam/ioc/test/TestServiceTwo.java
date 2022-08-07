package com.wade.adam.ioc.test;

import com.wade.adam.ioc.annotation.*;

@Service
public class TestServiceTwo {

    private final TestServiceOne serviceOne;

    @Autowired
    public TestServiceTwo(TestServiceOne serviceOne) {
        this.serviceOne = serviceOne;
    }

    @PostConstruct
    private void onInit(){ }

    @PreDestroy
    public void onDestroy(){ }

    @Bean
    public TestServiceThree testServiceThree(){
        return new TestServiceThree();
    }

}
