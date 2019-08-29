package bcz.dubbo.cloud.service;

import bcz.dubbo.cloud.api.StoreService;
import bcz.dubbo.cloud.entity.Order;
import bcz.dubbo.cloud.mapper.OrderMapper;
import bcz.dubbo.cloud.utils.IBatisKit;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Reference
    private StoreService storeService;

    @Override
    public String addOdr(Order order) {
        int cnt = orderMapper.insertSelective(order);
        if (cnt <= 0) {
            return "添加订单失败";
        }
        String errorMsg;
        if (order.getCount() != null && order.getCount() > 0
                && (errorMsg = storeService.subStore(order.getName(), order.getCount())) != null) {
            IBatisKit.rollback();
            return errorMsg;
        }
        return null;
    }
}
