package org.example.takeaway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.takeaway.entity.OrderDetail;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}