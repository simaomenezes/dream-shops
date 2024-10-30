package com.menezes.neto.dreamshops.service.cart;

import com.menezes.neto.dreamshops.exceptions.ResourceNotFoundException;
import com.menezes.neto.dreamshops.model.Cart;
import com.menezes.neto.dreamshops.repository.CartItemRepository;
import com.menezes.neto.dreamshops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository repository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getById(Long id) {
        Cart cart = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not Found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return repository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getById(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        repository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getById(id);
        return cart.getTotalAmount();
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
