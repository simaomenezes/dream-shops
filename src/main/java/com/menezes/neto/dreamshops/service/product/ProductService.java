package com.menezes.neto.dreamshops.service.product;

import com.menezes.neto.dreamshops.model.Product;
import com.menezes.neto.dreamshops.request.AddProductRequest;
import com.menezes.neto.dreamshops.request.ProductUpdateRequest;

import java.util.Collections;
import java.util.List;

public class ProductService implements IProductService {
    @Override
    public Product add(AddProductRequest product) {
        return null;
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
