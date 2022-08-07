package com.wade.adam.ioc.service;

import com.wade.adam.ioc.enums.DirectoryType;
import com.wade.adam.ioc.model.Directory;

import java.io.File;

public class DirectoryResolver {

    private static final String JAR_FILE_EXTENSION = ".jar";

    public Directory resolveDirectory(Class<?> startupClass){
        final String directory = getDirectory(startupClass);
        final DirectoryType directoryType = getDirectoryType(directory);
        return new Directory(directory, directoryType);
    }

    private String getDirectory(Class<?> clazz){
        return clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
    }

    private DirectoryType getDirectoryType(String directory){
        File file = new File(directory);
        if(!file.isDirectory() && directory.endsWith(JAR_FILE_EXTENSION)){
            return DirectoryType.JAR_FILE;
        }

        return DirectoryType.DIRECTORY;
    }

}
