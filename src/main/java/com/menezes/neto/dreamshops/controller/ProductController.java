package com.menezes.neto.dreamshops.controller;

import com.menezes.neto.dreamshops.dto.ProductDTO;
import com.menezes.neto.dreamshops.exceptions.ResourceNotFoundException;
import com.menezes.neto.dreamshops.model.Product;
import com.menezes.neto.dreamshops.request.AddProductRequest;
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
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
