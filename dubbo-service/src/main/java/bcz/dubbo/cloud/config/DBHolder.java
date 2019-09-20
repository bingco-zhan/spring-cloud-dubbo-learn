package bcz.dubbo.cloud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class DBHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBHolder.class);

    private static final ThreadLocal<String> dbLocal = new ThreadLocal<>();

    private static Integer totalSlaveDataSource;

    private static AtomicInteger count = new AtomicInteger(0);

    public static String KEY() {
        return dbLocal.get();
    }

    public static void RELOAD(String value) {
        // 轮询规则获取数据源
        if ("SLAVE".equals(value)) {
            int currentNum = count.getAndIncrement();
            if (currentNum > 999) {
                count.set(0);
            }
            value += (currentNum % totalSlaveDataSource + 1);
        }
        LOGGER.info("choose " + value + " datasource.");
        dbLocal.set(value);
    }

    public static void setTotalSlaveDataSource(Integer totalSlaveDataSource) {
        DBHolder.totalSlaveDataSource = totalSlaveDataSource;
    }
}
