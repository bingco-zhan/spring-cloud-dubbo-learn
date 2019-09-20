package bcz.dubbo.cloud.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置
 */
@EnableAspectJAutoProxy
@SpringBootConfiguration
public class RoutingDataConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DruidDataSource masterDataSource() {
        return new DruidDataSource();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave1")
    public DruidDataSource slaveDataSource_1() {
        return new DruidDataSource();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave2")
    public DruidDataSource slaveDataSource_2() {
        return new DruidDataSource();
    }

    @Bean
    public DataSource routingDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("MASTER", masterDataSource());
        targetDataSources.put("SLAVE1", slaveDataSource_1());
        targetDataSources.put("SLAVE2", slaveDataSource_2());
        BczRoutingDataSource dataSource = new BczRoutingDataSource();
        dataSource.setDefaultTargetDataSource(masterDataSource());
        dataSource.setTargetDataSources(targetDataSources);
        DBHolder.setTotalSlaveDataSource(2);
        return dataSource;
    }

    @Primary
    @Bean("dataSource")
    public DataSourceProxy dataSource(@Qualifier("routingDataSource") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }
}
