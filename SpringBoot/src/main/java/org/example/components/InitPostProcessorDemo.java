package org.example.components;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;

@Configuration
public class InitPostProcessorDemo implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if(beanName.contains("singletonOne")){
            System.out.println("InstantiationAwareBeanPostProcessor before execute"+beanClass+beanName);
        }
        return null;

    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(beanName.contains("singletonOne")){
            System.out.println("InstantiationAwareBeanPostProcessor after execute"+beanName);
        }
        return false;
    }
}
