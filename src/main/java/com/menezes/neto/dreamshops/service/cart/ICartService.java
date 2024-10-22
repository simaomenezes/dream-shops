package com.menezes.neto.dreamshops.service.cart;


import com.menezes.neto.dreamshops.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getById(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long initializeNewCart();
    Cart getByUserId(Long userId);
}
