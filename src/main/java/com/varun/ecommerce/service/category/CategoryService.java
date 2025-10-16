package com.varun.ecommerce.service.category;

import com.varun.ecommerce.exception.AlreadyExistsException;
import com.varun.ecommerce.exception.CategoryNotFoundException;
import com.varun.ecommerce.model.Category;
import com.varun.ecommerce.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepo categoryRepo;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("Category Not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional
                .of(category)
                .filter(c->!categoryRepo.existsByName(c.getName()))
                .map(categoryRepo::save)
                .orElseThrow(()->new AlreadyExistsException("Category is already present"));
    }

    @Override
    public Category updateCategory(Category category,Long id) {
        return Optional
                .ofNullable(getCategoryById(id))
                .map(oldCategory->{
                    oldCategory.setName(category.getName());
                    return categoryRepo.save(oldCategory);
                })
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete,()->{
            throw new CategoryNotFoundException("category not found");
        });
    }
}
