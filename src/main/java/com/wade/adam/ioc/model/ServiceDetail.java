package com.wade.adam.ioc.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class ServiceDetail<T> {

    private Class<T> serviceType;

    private Annotation annotation;

    private Constructor<T> targetConstructor;

    private T instance;

    private Method postConstructMethod;

    private Method preDestroyMethod;

    private Method[] beans;

    private final List<ServiceDetail<?>> dependentServices;

    public ServiceDetail() {
        this.dependentServices = new ArrayList<>();
    }

    public ServiceDetail(Class<T> serviceType,
                         Annotation annotation, Constructor<T> targetConstructor,
                         Method postConstructMethod, Method preDestroyMethod,
                         Method[] beans){

        this();
        this.setAnnotation(annotation);
        this.setTargetConstructor(targetConstructor);
        this.setPostConstructMethod(postConstructMethod);
        this.setPreDestroyMethod(preDestroyMethod);
        this.setBeans(beans);
    }


    public Class<T> getServiceType() {
        return serviceType;
    }

    public void setServiceType(Class<T> serviceType) {
        this.serviceType = serviceType;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Constructor<T> getTargetConstructor() {
        return targetConstructor;
    }

    public void setTargetConstructor(Constructor<T> targetConstructor) {
        this.targetConstructor = targetConstructor;
    }

    public T getInstance() {
        return instance;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }

    public Method getPostConstructMethod() {
        return postConstructMethod;
    }

    public void setPostConstructMethod(Method postConstructMethod) {
        this.postConstructMethod = postConstructMethod;
    }

    public Method getPreDestroyMethod() {
        return preDestroyMethod;
    }

    public void setPreDestroyMethod(Method preDestroyMethod) {
        this.preDestroyMethod = preDestroyMethod;
    }

    public Method[] getBeans() {
        return beans;
    }

    public void setBeans(Method[] beans) {
        this.beans = beans;
    }

    public List<ServiceDetail<?>> getDependentServices() {
        return Collections.unmodifiableList(dependentServices);
    }

    public void addDependentService(ServiceDetail<?> serviceDetail){
        this.dependentServices.add(serviceDetail);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceDetail<?> that = (ServiceDetail<?>) o;
        return Objects.equals(serviceType, that.serviceType) &&
                Objects.equals(annotation, that.annotation) &&
                Objects.equals(targetConstructor, that.targetConstructor) &&
                Objects.equals(instance, that.instance) &&
                Objects.equals(postConstructMethod, that.postConstructMethod) &&
                Objects.equals(preDestroyMethod, that.preDestroyMethod) &&
                Arrays.equals(beans, that.beans) &&
                Objects.equals(dependentServices, that.dependentServices);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(serviceType, annotation, targetConstructor, instance, postConstructMethod, preDestroyMethod, dependentServices);
        result = 31 * result + Arrays.hashCode(beans);
        return result;
    }
}
