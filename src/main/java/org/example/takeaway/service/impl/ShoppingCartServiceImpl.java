package org.example.takeaway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.takeaway.entity.ShoppingCart;
import org.example.takeaway.mapper.ShoppingCartMapper;
import org.example.takeaway.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
