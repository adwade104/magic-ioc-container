package com.wade.adam.ioc.util;

import com.wade.adam.ioc.model.ServiceDetail;

import java.util.Comparator;

public class ServiceDetailsConstructComparator implements Comparator<ServiceDetail> {
    @Override
    public int compare(ServiceDetail o1, ServiceDetail o2) {
        return Integer.compare(
                o1.getTargetConstructor().getParameterCount(),
                o2.getTargetConstructor().getParameterCount());
    }
}
