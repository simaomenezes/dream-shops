package com.menezes.neto.dreamshops.service.order;

import aj.org.objectweb.asm.commons.Remapper;
import com.menezes.neto.dreamshops.dto.OrderDTO;
import com.menezes.neto.dreamshops.enums.OrdersStatus;
import com.menezes.neto.dreamshops.exceptions.ResourceNotFoundException;
import com.menezes.neto.dreamshops.model.Cart;
import com.menezes.neto.dreamshops.model.Order;
import com.menezes.neto.dreamshops.model.OrderItem;
import com.menezes.neto.dreamshops.model.Product;
import com.menezes.neto.dreamshops.repository.OrderRepository;
import com.menezes.neto.dreamshops.repository.ProductRepository;
import com.menezes.neto.dreamshops.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

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

    @Override
    public OrderDTO getOrderById(Long orderId) {
        return repository.findById(orderId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public OrderDTO convertToDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getUserOrdersById(Long userId) {
        List<Order> orders = repository.findByUserId(userId);
        return orders.stream().map(this::convertToDTO).toList();
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
