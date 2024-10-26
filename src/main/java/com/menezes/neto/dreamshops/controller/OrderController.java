package com.menezes.neto.dreamshops.controller;

import com.menezes.neto.dreamshops.dto.OrderDTO;
import com.menezes.neto.dreamshops.exceptions.ResourceNotFoundException;
import com.menezes.neto.dreamshops.model.Order;
import com.menezes.neto.dreamshops.response.ApiResponse;
import com.menezes.neto.dreamshops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{userId}/user/order")
    public ResponseEntity<ApiResponse> getOrderByUser(@PathVariable Long userId){
        try {
            List<OrderDTO> ordersDTO = service.getUserOrdersById(userId);
            return ResponseEntity.ok(new ApiResponse("Item order Success!", ordersDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
            OrderDTO orderDTO = service.getOrderById(orderId);
            return ResponseEntity.ok(new ApiResponse("Item order success", orderDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
