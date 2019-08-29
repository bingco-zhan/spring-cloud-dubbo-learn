package bcz.dubbo.cloud.service;

import bcz.dubbo.cloud.api.StoreService;
import bcz.dubbo.cloud.entity.Store;
import bcz.dubbo.cloud.mapper.StoreMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

@Service(version = "1.0.0", group = "dubbo")
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public String subStore(String goodsName, int subNumber) {
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
