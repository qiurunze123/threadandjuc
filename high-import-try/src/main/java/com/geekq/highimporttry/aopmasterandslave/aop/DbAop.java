package com.geekq.highimporttry.aopmasterandslave.aop;

import com.geekq.highimporttry.aopmasterandslave.annotation.MyDataSource;
import com.geekq.highimporttry.aopmasterandslave.utils.DbUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class DbAop {

    @Before(value = "execution(* com.geekq.highimporttry.mapper..*.*(..))")
    public void before(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyDataSource annotation = method.getAnnotation(MyDataSource.class);
        if(annotation==null){
            DbUtil.setDb(DbUtil.master);
           return;
        }
        System.out.println(annotation.value()+"---------------");
        DbUtil.setDb(annotation.value());
    }

}
