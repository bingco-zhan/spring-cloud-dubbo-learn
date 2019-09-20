package bcz.dubbo.cloud.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class BczRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DBHolder.KEY();
    }
}
