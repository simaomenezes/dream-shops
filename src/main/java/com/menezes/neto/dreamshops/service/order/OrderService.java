package com.menezes.neto.dreamshops.service.order;

import com.menezes.neto.dreamshops.enums.OrdersStatus;
import com.menezes.neto.dreamshops.model.Cart;
import com.menezes.neto.dreamshops.model.Order;
import com.menezes.neto.dreamshops.model.OrderItem;
import com.menezes.neto.dreamshops.model.Product;
import com.menezes.neto.dreamshops.repository.OrderRepository;
import com.menezes.neto.dreamshops.repository.ProductRepository;
import com.menezes.neto.dreamshops.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository repository;
    private final CartService cartService;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public Order place(Long userId) {
        Cart cartFound = cartService.getByUserId(userId);
        Order order = createOrder(cartFound);
        List<OrderItem> orderItems = createOrderItem(order, cartFound);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Order savedOrder = repository.save(order);
        cartService.clearCart(cartFound.getId());
        return savedOrder;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream().map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<OrderItem> createOrderItem(Order order, Cart cartFound) {
        return cartFound.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();
    }

    private Order createOrder(Cart cartFound) {
        Order order = new Order();
        order.setUser(cartFound.getUser());
        order.setOrdersStatus(OrdersStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }
}
