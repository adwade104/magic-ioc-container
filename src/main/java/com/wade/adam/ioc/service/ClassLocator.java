package com.wade.adam.ioc.service;

import com.wade.adam.ioc.exceptions.ClassLocationException;

import java.util.Set;

public interface ClassLocator {

    Set<Class<?>> locateClasses(String directory) throws ClassLocationException;

}
