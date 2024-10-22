package com.menezes.neto.dreamshops.service.cart;

import com.menezes.neto.dreamshops.model.Cart;
import com.menezes.neto.dreamshops.model.CartItem;
import com.menezes.neto.dreamshops.model.Product;
import com.menezes.neto.dreamshops.repository.CartItemRepository;
import com.menezes.neto.dreamshops.repository.CartRepository;
import com.menezes.neto.dreamshops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{
    private final CartItemRepository repository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final ProductService productService;
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getById(cartId);
        Product product = productService.getById(productId);
        CartItem cartItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if(cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        repository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        return null;
    }
}
