package bcz.dubbo.cloud.service;

import bcz.dubbo.cloud.api.StoreService;
import bcz.dubbo.cloud.entity.Store;
import bcz.dubbo.cloud.mapper.StoreMapper;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

@Service(version = "1.0.0", group = "dubbo", filter = "sentinel.dubbo.consumer.filter")
public class StoreServiceImpl implements StoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private StoreMapper storeMapper;

    @Override
    @GlobalTransactional
    public String subStore(String goodsName, int subNumber) {
        LOGGER.info("Store Service ... xid: " + RootContext.getXID());

        // 上游超时熔断后是否扣除数量
        try {
            Thread.sleep(30000);
        } catch (InterruptedException exc1) {
            exc1.printStackTrace();
        }
        // 异常后上游是否回滚成功
//        int a = 10 / 0;
        Example example = new Example(Store.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", goodsName);
        Store store = storeMapper.selectOneByExample(example);
        if (store == null) {
            return "该商品尚未入库";
        }
        if (subNumber > store.getCnt()) {
            return "该商品库存不足";
        }
        int cnt = storeMapper.subStore(goodsName, subNumber);
        if (cnt <= 0) {
            return "更新库存失败";
        }
        return null;
    }
}
