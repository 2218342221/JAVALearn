package org.example.components;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostProcessorDemo implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof SingletonOne){
            System.out.println("BeanPostProcessor before execute"+beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof SingletonOne){
            System.out.println("BeanPostProcessor after execute"+beanName);
        }
        return bean;
    }
}
