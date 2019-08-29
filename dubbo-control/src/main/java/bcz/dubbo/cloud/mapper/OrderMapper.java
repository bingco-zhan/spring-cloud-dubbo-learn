package bcz.dubbo.cloud.mapper;

import bcz.dubbo.cloud.entity.Order;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface OrderMapper extends Mapper<Order>, MySqlMapper<Order> {
}