package com.menezes.neto.dreamshops.service.cart;


import com.menezes.neto.dreamshops.model.Cart;
import com.menezes.neto.dreamshops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getById(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);
    Cart getByUserId(Long userId);
}
