package org.example.takeaway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.example.takeaway.entity.OrderDetail;
import org.example.takeaway.mapper.OrderDetailMapper;
import org.example.takeaway.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}