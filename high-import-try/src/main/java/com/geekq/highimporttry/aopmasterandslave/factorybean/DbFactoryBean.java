package com.geekq.highimporttry.aopmasterandslave.factorybean;

import com.geekq.highimporttry.aopmasterandslave.utils.DbUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


public class DbFactoryBean implements FactoryBean<AbstractRoutingDataSource> {

    @Autowired
    @Qualifier("masterDataSource")
    DataSource masterDataSource;

    @Qualifier("slaveDataSource")
    @Autowired
    private DataSource slaveDataSource;

    @Nullable
    @Override
    public AbstractRoutingDataSource getObject() throws Exception {
        AbstractRoutingDataSource abstractRoutingDataSource=new AbstractRoutingDataSource() {
            @Nullable
            @Override
            protected Object determineCurrentLookupKey() {
                System.out.println(DbUtil.getDb());
                return DbUtil.getDb();
            }
        };

        abstractRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        Map<Object,Object> map=new HashMap<>();
        map.put("master",masterDataSource);
        map.put("slave",slaveDataSource);
        abstractRoutingDataSource.setTargetDataSources(map);
        abstractRoutingDataSource.afterPropertiesSet();
        return abstractRoutingDataSource;
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return AbstractRoutingDataSource.class;
    }
}
