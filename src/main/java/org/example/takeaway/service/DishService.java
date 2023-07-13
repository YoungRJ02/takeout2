package org.example.takeaway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.takeaway.common.R;
import org.example.takeaway.dto.DishDto;
import org.example.takeaway.entity.Dish;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

    public void removeWithFlavor(String id);
}
