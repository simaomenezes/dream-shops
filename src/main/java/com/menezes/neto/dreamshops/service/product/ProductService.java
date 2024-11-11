package com.menezes.neto.dreamshops.service.product;

import com.menezes.neto.dreamshops.dto.ProductDTO;
import com.menezes.neto.dreamshops.exceptions.AlreadyExistsException;
import com.menezes.neto.dreamshops.exceptions.ResourceNotFoundException;
import com.menezes.neto.dreamshops.model.Category;
import com.menezes.neto.dreamshops.model.Product;
import com.menezes.neto.dreamshops.repository.CategoryRepository;
import com.menezes.neto.dreamshops.repository.ProductRepository;
import com.menezes.neto.dreamshops.request.AddProductRequest;
import com.menezes.neto.dreamshops.request.ProductUpdateRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public Product add(AddProductRequest productRequest) {

        if(prodoctExists(productRequest.getName(), productRequest.getBrand())){
            throw new AlreadyExistsException(productRequest.getName()+ " "+productRequest.getBrand()+ " already exists, you may update this product instead!");
        }
        Category category = Optional.ofNullable(categoryRepository.findByName(productRequest.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(productRequest.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        productRequest.setCategory(category);
        return repository.save(createProduct(productRequest, category));
    }

    private boolean prodoctExists(String name, String brand) {
        return repository.existsByNameAndBrand(name, brand);
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
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteById(Long id) {
        repository.findById(id).ifPresentOrElse((p -> repository.delete(p)), () -> {throw  new ResourceNotFoundException("Product not found!");});
        //repository.findById(id).ifPresentOrElse(repository::delete, () -> {throw  new ResourceNotFoundException("Product not found!");});
    }

    @Override
    public Product update(ProductUpdateRequest product, Long id) {
        return repository.findById(id)
                .map(hasProduct -> updateProductFound(hasProduct, product))
                .map(repository :: save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    private Product updateProductFound(Product hasProduct, ProductUpdateRequest product) {
        hasProduct.setName(product.getName());
        hasProduct.setBrand(product.getBrand());
        hasProduct.setPrice(product.getPrice());
        hasProduct.setInventory(product.getInventory());
        hasProduct.setDescription(product.getDescription());
        Category category = categoryRepository.findByName(product.getCategory().getName());
        hasProduct.setCategory(category);
        return hasProduct;
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> getByCategory(String category) {
        return repository.findByCategoryName(category);
    }

    @Override
    public List<Product> getByBrand(String brand) {
        return repository.findByBrand(brand);
    }

    @Override
    public List<Product> getByCategoryAndBrand(String category, String brand) {
        return repository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public List<Product> getByBrandAndName(String brand, String name) {
        return repository.getByBrandAndName(brand, name);
    }

    @Override
    public Long countByBrandAndName(String brand, String name) {
        return repository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDTO).toList();
    }

    @Override
    public ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        return productDTO;
    }
}
