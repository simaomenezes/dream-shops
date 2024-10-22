package com.menezes.neto.dreamshops.service.cart;

import com.menezes.neto.dreamshops.model.Cart;
import com.menezes.neto.dreamshops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository repository;
    @Override
    public Cart getById(Long id) {
        return null;
    }

    @Override
    public void clearCart(Long id) {
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        return null;
    }

    @Override
    public Long initializeNewCart() {
        return 0L;
    }

    @Override
    public Cart getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

}
