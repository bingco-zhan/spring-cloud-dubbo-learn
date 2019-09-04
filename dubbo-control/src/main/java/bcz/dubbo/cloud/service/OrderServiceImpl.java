package bcz.dubbo.cloud.service;

import bcz.dubbo.cloud.api.StoreService;
import bcz.dubbo.cloud.entity.Order;
import bcz.dubbo.cloud.mapper.OrderMapper;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.remoting.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderMapper orderMapper;

    @Reference(version = "1.0.0", group = "dubbo", check = false, filter = "sentinel.dubbo.consumer.filter")
    private StoreService storeService;

    @Override
    @GlobalTransactional(rollbackFor = TimeoutException.class)
    public String addOdr(Order order) {
        LOGGER.info("Order Service ... xid: " + RootContext.getXID());
        int cnt = orderMapper.insertUseGeneratedKeys(order);
        if (cnt <= 0) {
            return "添加订单失败";
        }
        String errorMsg;
        if (order.getCount() != null && order.getCount() > 0
                && (errorMsg = storeService.subStore(order.getName(), order.getCount())) != null) {
            return errorMsg;
        }
        return null;
    }
}
