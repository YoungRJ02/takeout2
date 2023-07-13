package org.example.takeaway.dto;


import lombok.Data;
import org.example.takeaway.entity.Dish;
import org.example.takeaway.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
