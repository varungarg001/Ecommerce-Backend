package com.varun.ecommerce.controller;


import com.varun.ecommerce.exception.AlreadyExistsException;
import com.varun.ecommerce.exception.CategoryNotFoundException;
import com.varun.ecommerce.model.Category;
import com.varun.ecommerce.response.ApiResponse;
import com.varun.ecommerce.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/category/all")
    public ResponseEntity<ApiResponse> getAllCategory(){
        try {
            List<Category> allCategory = categoryService.getAllCategory();
            return ResponseEntity.ok(new ApiResponse("Found",allCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error! ",ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)));
        }
    }

    @PostMapping("/category/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
        try {
            Category result = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("category added successfully!",result));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
        try {
            Category categoryById = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Found!",categoryById));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try {
            Category categoryByName = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found!", categoryByName));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("delete success!", null));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody Category category){
        try {
            Category updated = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("update success!", updated));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }



}
