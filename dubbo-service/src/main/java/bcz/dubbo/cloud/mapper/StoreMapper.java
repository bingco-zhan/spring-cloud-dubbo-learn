package bcz.dubbo.cloud.mapper;

import bcz.dubbo.cloud.entity.Store;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface StoreMapper extends Mapper<Store>, MySqlMapper<Store> {

    @Update("update store set cnt = #{subNumber} where name = #{goodsName}")
    int subStore(@Param("goodsName") String goodsName, @Param("subNumber") int subNumber);
}