package com.wade.adam.ioc.service;

import com.wade.adam.ioc.annotation.*;
import com.wade.adam.ioc.model.ServiceDetail;
import com.wade.adam.ioc.config.CustomAnnotationConfig;
import com.wade.adam.ioc.util.ServiceDetailsConstructComparator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceScanningService {

    private final CustomAnnotationConfig customAnnotationConfig;

    public ServiceScanningService(CustomAnnotationConfig customAnnotationConfig) {
        this.customAnnotationConfig = customAnnotationConfig;
        this.customAnnotationConfig.getCustomBeanAnnotations().add(Bean.class);
        this.customAnnotationConfig.getCustomServiceAnnotations().add(Service.class);
    }

    public Set<ServiceDetail<?>> mapServices(Set<Class<?>> locatedClasses){
        final Set<ServiceDetail<?>> serviceDetails = new HashSet<>();
        final Set<Class<? extends Annotation>> customServiceAnnotations = customAnnotationConfig.getCustomServiceAnnotations();

        for(Class<?> clazz : locatedClasses){
            if(clazz.isInterface()){
                continue;
            }

            for(Annotation annotation : clazz.getAnnotations()){
                if(customServiceAnnotations.contains(annotation.annotationType())){

                    ServiceDetail<?> serviceDetail = new ServiceDetail(
                            clazz,
                            annotation,
                            findSuitableConstructorForClass(clazz),
                            findVoidMethodWithZeroParamsAndAnnotation(PostConstruct.class, clazz),
                            findVoidMethodWithZeroParamsAndAnnotation(PreDestroy.class, clazz),
                            findBeans(clazz));

                    serviceDetails.add(serviceDetail);
                }
            }
        }

        return serviceDetails.stream()
                .sorted(new ServiceDetailsConstructComparator())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Constructor<?> findSuitableConstructorForClass(Class<?> clazz){
        for(Constructor<?> constructor : clazz.getDeclaredConstructors()){
            if(constructor.isAnnotationPresent(Autowired.class)){
                constructor.setAccessible(true);
                return constructor;
            }
        }
        return clazz.getConstructors()[0];
    }

    private Method findVoidMethodWithZeroParamsAndAnnotation(Class<? extends Annotation> annotation, Class<?> clazz){
        for(Method method : clazz.getDeclaredMethods()) {
            if(method.getParameterCount() != 0 || (method.getReturnType() != void.class && method.getReturnType() != Void.class)){
                continue;
            }

            if(method.isAnnotationPresent(annotation)) {
                method.setAccessible(true);
                return method;
            }
        }
        return null;
    }

    private Method[] findBeans(Class<?> clazz){
        final Set<Class<? extends Annotation>> beanAnnotations = customAnnotationConfig.getCustomBeanAnnotations();
        final Set<Method> beanMethods = new HashSet<>();

        for(Method method : clazz.getDeclaredMethods()){
            if(method.getParameterCount() != 0 || method.getReturnType() == void.class || method.getReturnType() == Void.class){
                continue;
            }

            for(Class<? extends Annotation> beanAnnotation : beanAnnotations){
                if(method.isAnnotationPresent(beanAnnotation)){
                    beanMethods.add(method);
                }
            }

        }

        return beanMethods.toArray(Method[]::new);
    }

}
