package com.example.Mondia.controller;

import com.example.Mondia.exception.ResourceNotFoundException;
import com.example.Mondia.model.Category;
import com.example.Mondia.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

 @CrossOrigin("http://localhost:4200")
@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

 
 
     @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping("/categories")
    public Category createCategory(@Valid @RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/categories/{categoryId}")
    public Category updateCategory(@PathVariable Long categoryId,
                                   @Valid @RequestBody Category categoryRequest) {
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    category.setName(categoryRequest.getName());
                   // category.setDescription(questionRequest.getDescription());
                    return categoryRepository.save(category);
                }).orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));
    }


    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    categoryRepository.delete(category);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));
    }
}
