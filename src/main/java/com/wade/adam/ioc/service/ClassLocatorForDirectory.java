package com.wade.adam.ioc.service;

import com.wade.adam.ioc.constants.Constants;
import com.wade.adam.ioc.exceptions.ClassLocationException;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ClassLocatorForDirectory implements ClassLocator {

    private static final String INVALID_DIRECTORY_MSG = "Invalid directory '%s'.";

    private final Set<Class<?>> locatedClasses;

    public ClassLocatorForDirectory() {
        this.locatedClasses = new HashSet<>();
    }

    @Override
    public Set<Class<?>> locateClasses(String directory) throws ClassLocationException {
        locatedClasses.clear();

        File file = new File(directory);
        if (!file.isDirectory()) {
            throw new ClassLocationException(String.format(INVALID_DIRECTORY_MSG, directory));
        }

        try {
            for (File innerFile : file.listFiles()) {
                scanDir(innerFile, "");
            }
        } catch (ClassNotFoundException e) {
            throw new ClassLocationException(e.getMessage(), e);
        }

        return locatedClasses;
    }

    private void scanDir(File file, String packageName) throws ClassNotFoundException {
        if (file.isDirectory()) {
            packageName += file.getName() + ".";
            for (File innerFile : file.listFiles()) {
                scanDir(innerFile, packageName);
            }
        } else {
            if (!file.getName().endsWith(Constants.JAVA_BINARY_EXTENSION)) {
                return;
            }

            final String className = packageName + file.getName().replace(Constants.JAVA_BINARY_EXTENSION, "");
            locatedClasses.add(Class.forName(className));
        }
    }

}
