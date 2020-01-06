package com.pilaf.mybatis.configuration;


import com.pilaf.common.DatabaseContextHolder;
import com.pilaf.common.DatabaseEnum;
import com.pilaf.mybatis.aspect.AspectOrderConstants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
 * @author: dufeng3
 * @create: 2019-12-19 14:47
 */
@Configuration
@EnableTransactionManagement(order = AspectOrderConstants.EnableTransactionManagementOrder)
public class DataSourcesConfiguration {

    /**
     * 配置连接池信息
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public HikariConfig db1HikariConfig(){
        return new HikariConfig();
    }

    @Bean
    public HikariDataSource db1DataSource(@Qualifier("db1HikariConfig") HikariConfig hikariConfig){
        return new HikariDataSource(hikariConfig);
    }

    /**
     * 配置连接池信息
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public HikariConfig db2HikariConfig(){
        return new HikariConfig();
    }

    @Bean
    public HikariDataSource db2DataSource(@Qualifier("db2HikariConfig") HikariConfig hikariConfig){
        return new HikariDataSource(hikariConfig);
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
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();

            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
            configuration.setCacheEnabled(false);

            bean.setConfiguration(configuration);
            bean.setDataSource(dynamicDataSource(db1DataSource(db1HikariConfig()),db2DataSource(db2HikariConfig())));
            bean.setMapperLocations(
                    new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml")
            );
            //TypeAliasesPackage支持通配符
            bean.setTypeAliasesPackage("com.pilaf.mybatis.*.entity");

            sqlSessionFactory = bean.getObject();
        } catch (Exception e) {
            System.out.println("创建sqlSessionFactory异常");
            //因为程序已经没法正常进行下去了，需要人工干预，所以抛出运行时异常
            throw new RuntimeException(e);
        }

        return sqlSessionFactory;
    }



}
