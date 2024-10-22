package com.menezes.neto.dreamshops.service.cart;

import com.menezes.neto.dreamshops.model.Cart;
import com.menezes.neto.dreamshops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository repository;

    private final AtomicLong cartIdGenerator = new AtomicLong(0);
    @Override
    public Cart getById(Long id) {
        return repository.findById(id).get();
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
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return repository.save(newCart).getId();
    }

    @Override
    public Cart getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

}
