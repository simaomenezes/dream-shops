package com.menezes.neto.dreamshops.controller;

import com.menezes.neto.dreamshops.exceptions.AlreadyExistsException;
import com.menezes.neto.dreamshops.model.Category;
import com.menezes.neto.dreamshops.response.ApiResponse;
import com.menezes.neto.dreamshops.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService service;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> all(){
        try {
            List<Category> categories = service.getAll();
            return ResponseEntity.ok(new ApiResponse("success", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR", null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@RequestBody Category category){
        try {
            Category categoryCreated = service.add(category);
            return ResponseEntity.ok(new ApiResponse("success", categoryCreated));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        try {
            Category categoryFound = service.getById(id);
            return ResponseEntity.ok(new ApiResponse("success", categoryFound));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by-name")
    public ResponseEntity<ApiResponse> findByName(@RequestParam String name){
        try {
            Category categoryFound = service.findByName(name);
            return ResponseEntity.ok(new ApiResponse("success", categoryFound));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        try {
            Category catFound = service.getById(id);
            service.delete(catFound.getId());
            return ResponseEntity.ok(new ApiResponse("success", catFound));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody Category category){
        try {
            Category catFound = service.update(category, id);
            return ResponseEntity.ok(new ApiResponse("success", catFound));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
