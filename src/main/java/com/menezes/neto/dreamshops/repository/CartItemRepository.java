package com.menezes.neto.dreamshops.repository;

import com.menezes.neto.dreamshops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long cartId);
    List<CartItem> findByProductId(Long productId);
}
