package com.pilaf.mybatisplus.configuration;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.pilaf.common.DatabaseContextHolder;
import com.pilaf.common.DatabaseEnum;
import com.pilaf.mybatisplus.aspect.AspectOrderConstants;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

/**
 * @description: 配置多个数据源，每个数据源对应一个mysql的database
 * @author: pilaf
 * @create: 2019-12-19 14:47
 */
@SuppressWarnings("all")
@Configuration
@EnableTransactionManagement(order = AspectOrderConstants.EnableTransactionManagementOrder)
public class DataSourcesConfiguration {



    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource db1DataSource(){
        return DataSourceBuilder.create().build();
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource db2DataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * DataSource的bean，一定不要忘了@Primary注解
     * @param db1DataSource
     * @param db2DataSource
     * @return 动态数据源
     */
    @Bean
    @Primary
    public DataSource dynamicDataSource(@Qualifier("db1DataSource") DataSource db1DataSource,
                                        @Qualifier("db2DataSource") DataSource db2DataSource){
        AbstractRoutingDataSource dynamicDataSource = new AbstractRoutingDataSource(){
            @Override
            protected Object determineCurrentLookupKey() {
                String database = DatabaseContextHolder.getDatabase();
                if(StringUtils.isEmpty(database)){
                    System.out.println("使用默认数据库db1");
                }else {
                    System.out.println("使用数据库:"+database);
                }

                return database;
            }
        };

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseEnum.db1.getValue(), db1DataSource);
        targetDataSources.put(DatabaseEnum.db2.getValue(), db2DataSource);

        dynamicDataSource.setTargetDataSources(targetDataSources);
        //默认的是db1数据源
        dynamicDataSource.setDefaultTargetDataSource(db1DataSource);

        return dynamicDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() {
        System.out.println("创建sqlSessionFactory");
        SqlSessionFactory sqlSessionFactory;
        try {

            //注意MyBatis-Plus和MyBatis的sqlSessionFactory的创建过程有区别
            //这儿不要用原生的SqlSessionFactory，否则会报BindingException: Invalid bound statement (not found)
            MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();

            MybatisConfiguration configuration = new MybatisConfiguration();
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
            configuration.setCacheEnabled(false);

            bean.setConfiguration(configuration);
            bean.setDataSource(dynamicDataSource(db1DataSource(),db2DataSource()));
            bean.setMapperLocations(
                    new PathMatchingResourcePatternResolver().getResources("classpath:com/pilaf/mybatisplus/*/xml/mapper/*.xml")
            );
            //TypeAliasesPackage支持通配符
            bean.setTypeAliasesPackage("com.pilaf.mybatisplus.*.entity");

            sqlSessionFactory = bean.getObject();
        } catch (Exception e) {
            System.out.println("创建sqlSessionFactory异常");
            //因为程序已经没法正常进行下去了，需要人工干预，所以抛出运行时异常
            throw new RuntimeException(e);
        }

        return sqlSessionFactory;
    }



}
