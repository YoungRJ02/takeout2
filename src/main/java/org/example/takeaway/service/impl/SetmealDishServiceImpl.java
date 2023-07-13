package org.example.takeaway.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.takeaway.entity.SetmealDish;
import org.example.takeaway.mapper.SetmealDishMapper;
import org.example.takeaway.service.SetmealDishService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
