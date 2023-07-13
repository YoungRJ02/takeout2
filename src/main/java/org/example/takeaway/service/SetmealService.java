package org.example.takeaway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.takeaway.dto.SetmealDto;
import org.example.takeaway.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    public void saveWithDish(SetmealDto setmealDto);

    public void deleteWithDish(List<Long> ids);
}
