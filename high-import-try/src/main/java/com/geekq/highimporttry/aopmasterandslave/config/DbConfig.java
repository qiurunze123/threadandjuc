package com.geekq.highimporttry.aopmasterandslave.config;

import com.geekq.highimporttry.aopmasterandslave.factorybean.DbFactoryBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;


public class DbConfig implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        //注册MyMapperFactoryBean
        //为什么不用其他方式注册MyMapperFactoryBean呢    因为这里是代码形式，可以循环，产生多个
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DbFactoryBean.class);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        //注册
        beanDefinitionRegistry.registerBeanDefinition("dbFactoryBean",beanDefinition);
    }

}
