package com.menezes.neto.dreamshops.controller;

import com.menezes.neto.dreamshops.model.Product;
import com.menezes.neto.dreamshops.request.AddProductRequest;
import com.menezes.neto.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;
    
    @PostMapping("/add")
    public void addProduct(@RequestBody AddProductRequest productRequest){
        Product productCreated = productService.add(productRequest);
    }
}
