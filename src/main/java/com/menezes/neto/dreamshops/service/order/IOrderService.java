package com.menezes.neto.dreamshops.service.order;

import com.menezes.neto.dreamshops.model.Order;

public interface IOrderService {
    Order place(Long userId);
}
