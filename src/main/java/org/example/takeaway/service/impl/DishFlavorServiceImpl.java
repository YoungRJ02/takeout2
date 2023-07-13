package org.example.takeaway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.takeaway.entity.DishFlavor;
import org.example.takeaway.mapper.DishFlavorMapper;
import org.example.takeaway.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
