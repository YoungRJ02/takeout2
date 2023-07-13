package org.example.takeaway.dto;

import lombok.Data;
import org.example.takeaway.entity.OrderDetail;
import org.example.takeaway.entity.Orders;

import java.util.List;

@Data
public class OrderDto extends Orders {
    private String userName;

    private String phone;

    private String address;

    private String consignee;
    private Integer sumNum;

    private List<OrderDetail> orderDetails;
}
