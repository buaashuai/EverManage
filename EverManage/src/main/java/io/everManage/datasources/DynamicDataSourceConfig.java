package io.everManage.datasources;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置多数据源
 *
 * @author wangshuai
 * @email shuaiwang126@163.com
 * @date 2017/8/19 0:41
 */
@Configuration
public class DynamicDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSource firstDataSource() {
        //数据源1，读取spring.datasource.druid.first下的配置信息
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDataSource() {
        //数据源2，读取spring.datasource.druid.second下的配置信息
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 加了@Primary注解，表示指定DynamicDataSource为Spring的数据源, 因为DynamicDataSource是继承与AbstractRoutingDataSource，
     * 而AbstractR outingDataSource又是继承于AbstractDataSource，AbstractDataSource实现了统一 的DataSource接口，所以DynamicDataSource也可以当做DataSource使用
     *
     * @param firstDataSource
     * @param secondDataSource
     * @return
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource firstDataSource, DataSource secondDataSource) {
        Map<String, DataSource> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, firstDataSource);
        targetDataSources.put(DataSourceNames.SECOND, secondDataSource);
        return new DynamicDataSource(firstDataSource, targetDataSources);
    }
}
