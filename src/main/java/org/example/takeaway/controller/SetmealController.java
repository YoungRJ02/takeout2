package org.example.takeaway.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.example.takeaway.common.R;
import org.example.takeaway.dto.SetmealDto;
import org.example.takeaway.entity.Category;
import org.example.takeaway.entity.Setmeal;
import org.example.takeaway.service.CategoryService;
import org.example.takeaway.service.SetmealDishService;
import org.example.takeaway.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> add(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> pageinfo = new Page(page, pageSize);
        Page<SetmealDto> dtoPage = new Page();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageinfo);

        BeanUtils.copyProperties(pageinfo, dtoPage, "records");
        List<Setmeal> records = pageinfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) ->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null){
                String name1 = category.getName();
                setmealDto.setCategoryName(name1);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.deleteWithDish(ids);
        return R.success("删除成功");
    }

    @PostMapping("/status/{status}")
    public R<String> sell(@PathVariable int status, String[] ids){
        for (String id : ids){
            Setmeal setmeal = setmealService.getById(id);
            setmeal.setStatus(status);
            setmealService.updateById(setmeal);
        }
        return R.success("修改成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }
}
