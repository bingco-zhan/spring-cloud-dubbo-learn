package bcz.dubbo.cloud.mapper;

import bcz.dubbo.cloud.entity.Order;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface OrderMapper extends Mapper<Order>, MySqlMapper<Order> {
}