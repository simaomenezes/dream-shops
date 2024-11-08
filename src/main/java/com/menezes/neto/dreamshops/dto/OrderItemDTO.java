package com.menezes.neto.dreamshops.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
}
