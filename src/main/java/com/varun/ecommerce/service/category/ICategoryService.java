package com.varun.ecommerce.service.category;

import com.varun.ecommerce.model.Category;

import java.util.List;

public interface ICategoryService {
     Category getCategoryById(Long id);
     Category getCategoryByName(String name);
     List<Category> getAllCategory();
     Category addCategory(Category category);
     Category updateCategory(Category category,Long id);
     void deleteCategoryById(Long id);

}
