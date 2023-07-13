package org.example.takeaway.dto;


import lombok.Data;
import org.example.takeaway.entity.Setmeal;
import org.example.takeaway.entity.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
