package com.menezes.neto.dreamshops.service.order;

import com.menezes.neto.dreamshops.dto.OrderDTO;
import com.menezes.neto.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order place(Long userId);
    OrderDTO getOrderById(Long orderId);
    List<OrderDTO> getUserOrdersById(Long userId);
}
