package com.menezes.neto.dreamshops.service.product;

import com.menezes.neto.dreamshops.model.Category;
import com.menezes.neto.dreamshops.model.Product;
import com.menezes.neto.dreamshops.repository.CategoryRepository;
import com.menezes.neto.dreamshops.repository.ProductRepository;
import com.menezes.neto.dreamshops.request.AddProductRequest;
import com.menezes.neto.dreamshops.request.ProductUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    @Override
    public Product add(AddProductRequest productRequest) {
        Category category = Optional.ofNullable(categoryRepository.findByName(productRequest.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(productRequest.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        productRequest.setCategory(category);
        return productRepository.save(createProduct(productRequest, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Product update(ProductUpdateRequest product, Long id) {
        return null;
    }

    @Override
    public List<Product> getAll() {
        return Collections.emptyList();
    }

    @Override
    public List<Product> getByCategory(String category) {
        return Collections.emptyList();
    }

    @Override
    public List<Product> getByBrand(String brand) {
        return Collections.emptyList();
    }

    @Override
    public List<Product> getByCategoryAndBrand(String category, String brand) {
        return Collections.emptyList();
    }

    @Override
    public List<Product> getByName(String name) {
        return Collections.emptyList();
    }

    @Override
    public List<Product> getByBrandAndName(String brand, String name) {
        return Collections.emptyList();
    }

    @Override
    public Long countByBrandAndName(String brand, String name) {
        return 0L;
    }

    @Override
    public List<Product> getConvertedProducts(List<Product> products) {
        return Collections.emptyList();
    }
}
