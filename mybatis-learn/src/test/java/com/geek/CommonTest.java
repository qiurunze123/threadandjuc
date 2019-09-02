package com.geek;

import com.geek.JDKProxy.XiaoJuZi;
import com.geek.JDKProxy.XiaoQiu;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogException;
import org.apache.ibatis.logging.LogFactory;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author 邱润泽 bullock
 */
public class CommonTest {


    @Test
    public void testConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        Constructor<XiaoQiu> candidate = XiaoQiu.class.getConstructor();
        XiaoQiu xiaoJuZi = (XiaoQiu) candidate.newInstance(LogFactory.class.getName());
    }



}
