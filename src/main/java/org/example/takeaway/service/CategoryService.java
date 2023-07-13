package org.example.takeaway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.takeaway.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long ids);
}
