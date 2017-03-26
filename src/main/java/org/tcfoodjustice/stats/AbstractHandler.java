package org.tcfoodjustice.stats;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.ParameterizedType;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
public class AbstractHandler<T> {
    protected ApplicationContext applicationContext;

    public AbstractHandler() {

        /**
         * Gets config class to create an Application context
         */
        Class typeParameterClass = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0]);

        /**
         * Check if T has @Configuration annotation,
         * if no throws an exception
         */
        if (!typeParameterClass.isAnnotationPresent(Configuration.class)) {
            throw new RuntimeException(typeParameterClass + " is not a @Configuration class");
        }
        applicationContext = new AnnotationConfigApplicationContext(typeParameterClass);
    }
}
