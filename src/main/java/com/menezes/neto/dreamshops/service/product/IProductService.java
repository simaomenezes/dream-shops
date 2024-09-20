package com.menezes.neto.dreamshops.service.product;

import com.menezes.neto.dreamshops.model.Product;
import com.menezes.neto.dreamshops.request.AddProductRequest;
import com.menezes.neto.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product add(AddProductRequest product);
    Product getById(Long id);
    void deleteById(Long id);
    Product update(ProductUpdateRequest product, Long id);
    List<Product> getAll();
    List<Product> getByCategory(String category);
    List<Product> getByBrand(String brand);
    List<Product> getByCategoryAndBrand(String category, String brand);
    List<Product> getByName(String name);
    List<Product> getByBrandAndName(String brand, String name);
    Long countByBrandAndName(String brand, String name);

    List<Product> getConvertedProducts(List<Product> products);
}
