package org.example.takeaway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.takeaway.common.CustomException;
import org.example.takeaway.entity.Category;
import org.example.takeaway.entity.Dish;
import org.example.takeaway.entity.Setmeal;
import org.example.takeaway.mapper.CategoryMapper;
import org.example.takeaway.service.CategoryService;
import org.example.takeaway.service.DishService;
import org.example.takeaway.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, ids);
        int count = dishService.count(dishLambdaQueryWrapper);
        if(count > 0){
            throw new CustomException("当前分类项关联了菜品，不能删除");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, ids);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count1 > 0){
            throw new CustomException("当前套餐项关联了菜品，不能删除");
        }
        super.removeById(ids);
    }
}
