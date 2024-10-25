package com.menezes.neto.dreamshops.service.category;

import com.menezes.neto.dreamshops.model.Category;
import java.util.List;
import java.util.Set;

public interface ICategoryService {

    Category getById(Long id);
    Category findByName(String name);
    List<Category> getAll();
    Category add(Category category);
    Category update(Category category, Long id);
    void delete(Long id);
    Set<Category> getAll1();
}
