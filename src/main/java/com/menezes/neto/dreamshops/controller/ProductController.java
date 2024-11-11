package com.menezes.neto.dreamshops.controller;

import com.menezes.neto.dreamshops.dto.ProductDTO;
import com.menezes.neto.dreamshops.exceptions.AlreadyExistsException;
import com.menezes.neto.dreamshops.exceptions.ResourceNotFoundException;
import com.menezes.neto.dreamshops.model.Product;
import com.menezes.neto.dreamshops.request.AddProductRequest;
import com.menezes.neto.dreamshops.request.ProductUpdateRequest;
import com.menezes.neto.dreamshops.response.ApiResponse;
import com.menezes.neto.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService service;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(){
        List<Product> products = service.getAll();
        List<ProductDTO> productDTO = service.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("success", productDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        try {
            Product productFound = service.getById(id);
            ProductDTO productDTO = service.convertToDTO(productFound);
            return ResponseEntity.ok(new ApiResponse("success", productDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse((e.getMessage()), null));
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@RequestBody AddProductRequest productRequest){
        try {
            Product productCreated = service.add(productRequest);
            ProductDTO productDTOCreated = service.convertToDTO(productCreated);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Add product success!", productDTOCreated));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> update(@RequestBody ProductUpdateRequest request, @PathVariable Long id) {
        try {
            Product product = service.update(request, id);
            ProductDTO productDTO = service.convertToDTO(product);
            return ResponseEntity.ok(new ApiResponse("Update product success!!!!", productDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Delete product with success!!!", id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            List<Product> productListFound = service.getByBrandAndName(brand, name);
            List<ProductDTO> productDTOList = service.getConvertedProducts(productListFound);
            return ResponseEntity.ok(new ApiResponse("Success", productDTOList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/brand")
    public ResponseEntity<ApiResponse> getByBrand(@RequestParam String brand) {
        try {
            List<Product> productListFound = service.getByBrand(brand);
            List<ProductDTO> productDTOList = service.getConvertedProducts(productListFound);
            return ResponseEntity.ok(new ApiResponse("success", productDTOList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/{name}/products")
    public ResponseEntity<ApiResponse> getByName(@PathVariable String name){
        try {
            List<Product> productsList = service.getByName(name);
            List<ProductDTO> productsDTOList = service.getConvertedProducts(productsList);
            return ResponseEntity.ok(new ApiResponse("success", productsDTOList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/{category}/all/products")
    public ResponseEntity<ApiResponse> getByCategory(@PathVariable String category){
        try {
            List<Product> productList = service.getByCategory(category);
            List<ProductDTO> productDTOList = service.getConvertedProducts(productList);
            return ResponseEntity.ok(new ApiResponse("success", productDTOList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countByBrandAndName(@RequestParam String brand, @RequestParam String name){
        try {
            Long countProduct = service.countByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("success", countProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        try {
            List<Product> productList = service.getByCategoryAndBrand(category, brand);
            List<ProductDTO> productDTOList = service.getConvertedProducts(productList);
            return  ResponseEntity.ok(new ApiResponse("success", productDTOList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
