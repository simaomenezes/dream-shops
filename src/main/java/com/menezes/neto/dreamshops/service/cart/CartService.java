package com.menezes.neto.dreamshops.service.cart;

import com.menezes.neto.dreamshops.exceptions.ResourceNotFoundException;
import com.menezes.neto.dreamshops.model.Cart;
import com.menezes.neto.dreamshops.model.User;
import com.menezes.neto.dreamshops.repository.CartItemRepository;
import com.menezes.neto.dreamshops.repository.CartRepository;
import com.menezes.neto.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository repository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;

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
        cart.clearCart();
        repository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getById(id);
        return cart.getTotalAmount();
    }


    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getByUserId(user.getId())).orElseGet(() ->{
            Cart cart = new Cart();
            cart.setUser(user);
            return repository.save(cart);
        });
    }

    @Override
    public Cart getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

}
