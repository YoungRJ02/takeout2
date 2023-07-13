package org.example.takeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.example.takeaway.common.R;
import org.example.takeaway.dto.OrderDto;
import org.example.takeaway.entity.AddressBook;
import org.example.takeaway.entity.OrderDetail;
import org.example.takeaway.entity.Orders;
import org.example.takeaway.service.AddressBookService;
import org.example.takeaway.service.OrderDetailService;
import org.example.takeaway.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number, String beginTime,String endTime){
        Page<Orders> pageinfo = new Page<>(page, pageSize);
        Page<OrderDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(number), Orders::getNumber, number);
        queryWrapper.gt(StringUtils.isNotEmpty(beginTime), Orders::getOrderTime, beginTime);
        queryWrapper.lt(StringUtils.isNotEmpty(endTime), Orders::getCheckoutTime, endTime);
        queryWrapper.orderByAsc(Orders::getOrderTime);
        orderService.page(pageinfo, queryWrapper);

        BeanUtils.copyProperties(pageinfo, dtoPage, "records");
        List<Orders> records = pageinfo.getRecords();
        List<OrderDto> list = records.stream().map((item) ->{
            OrderDto orderDto = new OrderDto();
            String consignee = item.getConsignee();
            BeanUtils.copyProperties(item, orderDto);
            orderDto.setUserName(consignee);
            return orderDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    @PutMapping
    public R<String> statu(@RequestBody Orders orders){
        Long id = orders.getId();
        Integer status = orders.getStatus();
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getId, id);
        Orders one = orderService.getOne(queryWrapper);
        one.setStatus(status);
        orderService.updateById(one);
        return R.success("修改成功");
    }

    @Transactional
    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize){
        Page<Orders> pageinfo = new Page<>(page, pageSize);
        Page<OrderDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Orders::getOrderTime);
        orderService.page(pageinfo, queryWrapper);

        BeanUtils.copyProperties(pageinfo, dtoPage, "records");
        List<Orders> records = pageinfo.getRecords();
        List<OrderDto> list = records.stream().map((item) ->{
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(item, orderDto);
            Long id = item.getId();
            Orders orders = orderService.getById(id);
            String number = orders.getNumber();
            LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OrderDetail::getOrderId, number);
            List<OrderDetail> list1 = orderDetailService.list(lambdaQueryWrapper);
            int sum = 0;
            for(OrderDetail l : list1){
                sum += l.getNumber();
            }
            orderDto.setSumNum(sum);
            return orderDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    @Transactional
    @PostMapping("/again")
    public R<String> again(@RequestBody Orders orders){
        Long id = orders.getId();
        Orders order = orderService.getById(id);
        long id1 = IdWorker.getId();
        order.setId(id1);
        String number = String.valueOf(IdWorker.getId());
        order.setNumber(number);
        order.setOrderTime(LocalDateTime.now());
        order.setCheckoutTime(LocalDateTime.now());
        order.setStatus(2);
        orderService.save(order);
        LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderDetail::getOrderId, id);
        List<OrderDetail> list = orderDetailService.list(lambdaQueryWrapper);
        list.stream().map((item) ->{
            long id2 = IdWorker.getId();
            item.setOrderId(id1);
            item.setId(id2);
            return item;
        }).collect(Collectors.toList());
        orderDetailService.saveBatch(list);
        return R.success("再来一单");
    }
}