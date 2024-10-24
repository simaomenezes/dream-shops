package com.menezes.neto.dreamshops.controller;

import com.menezes.neto.dreamshops.model.Order;
import com.menezes.neto.dreamshops.response.ApiResponse;
import com.menezes.neto.dreamshops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService service;

    @GetMapping("/order")
    public ResponseEntity<ApiResponse> creteOrder(@RequestParam Long userId){
        try {
            Order order = service.place(userId);
            return ResponseEntity.ok(new ApiResponse("Order success", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Ocured!", e.getMessage()));
        }
    }
}
