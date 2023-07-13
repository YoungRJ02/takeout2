package org.example.takeaway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.takeaway.entity.Orders;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
