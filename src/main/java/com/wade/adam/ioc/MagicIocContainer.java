package com.wade.adam.ioc;

import com.wade.adam.ioc.config.MagicConfig;
import com.wade.adam.ioc.enums.DirectoryType;
import com.wade.adam.ioc.model.ServiceDetail;
import com.wade.adam.ioc.service.*;
import com.wade.adam.ioc.model.Directory;

import java.util.Set;

public class MagicIocContainer {

    public static void main(String[] args){
        run(MagicIocContainer.class);
    }

    public static void run(Class<?> startupClass){
        run(startupClass, new MagicConfig());
    }

    public static void run(Class<?> startupClass, MagicConfig magicConfig){
        DirectoryResolver directoryResolver = new DirectoryResolver();
        Directory directory = directoryResolver.resolveDirectory(startupClass);

        ClassLocator classLocator = new ClassLocatorForDirectory();
        if(DirectoryType.JAR_FILE.equals(directory.getDirectoryType())){
            classLocator = new ClassLocatorForJarFile();
        }

        ServiceScanningService serviceScanningService = new ServiceScanningService(magicConfig.annotations());
        Set<Class<?>> locatedClasses = classLocator.locateClasses(directory.getDirectory());

        Set<ServiceDetail<?>> serviceDetails = serviceScanningService.mapServices(locatedClasses);
        System.out.println(serviceDetails);
    }

}
