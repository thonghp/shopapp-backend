package com.example.shopapp.controllers;

import com.example.shopapp.dtos.CategoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @GetMapping("") // http://localhost:8088/api/v1/categories?page=1&limit=5
    public ResponseEntity<String> getAllCategories(@RequestParam int page, @RequestParam int limit) {
        return ResponseEntity.ok(String.format("Get all categories with page = %d and limit = %d", page, limit));
    }

    @PostMapping("")
    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessage);
        }
        return ResponseEntity.ok("Create category " + categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id) {
        return ResponseEntity.ok("update category " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok("Delete category " + id);
    }
}
