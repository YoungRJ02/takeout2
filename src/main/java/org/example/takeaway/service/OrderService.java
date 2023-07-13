package org.example.takeaway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.takeaway.common.R;
import org.example.takeaway.entity.Orders;

public interface OrderService extends IService<Orders> {

    public void submit(Orders orders);
}
