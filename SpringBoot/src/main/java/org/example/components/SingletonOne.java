package org.example.components;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
public class SingletonOne implements BeanNameAware, BeanFactoryAware, ApplicationContextAware,  InitializingBean, DisposableBean {


    public SingletonOne(PostProcessorDemo demo){
        System.out.println("construct"+demo);
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("BeanNameAware execute "+s);
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware execute"+beanFactory );
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean execute");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean execute");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextAware execute");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("post Construct execute");
    }

    @PreDestroy
    public void destroy1(){
        System.out.println("destroy1");
    }
}
