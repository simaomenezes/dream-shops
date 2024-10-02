package com.menezes.neto.dreamshops.service.category;

import com.menezes.neto.dreamshops.exceptions.ResourceNotFoundException;
import com.menezes.neto.dreamshops.model.Category;
import com.menezes.neto.dreamshops.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CategoryService implements ICategory{

    private final CategoryRepository repository;

    @Override
    public Category getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category add(Category category) {
        return null;
    }

    @Override
    public Category update(Category category, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Set<Category> getAll1() {
        return Set.of();
    }
}
